package de.belmega.biohazard.core.country;


import de.belmega.biohazard.server.persistence.state.CountryState;
import de.belmega.biohazard.server.persistence.state.DiseaseState;
import de.belmega.biohazard.server.persistence.state.InfectionState;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Country {

    private CountryState state;


    public Country(CountryState countryState) {
        this.state = countryState;
    }

    public void tick() {
        state.getRoutes().forEach(this::spreadDiseasesTravelling);

        growPopulation();

        state.getInfections().forEach(this::applyDiseaseToPopulation);
    }

    /**
     * Calculate the new population based on the population growth factor.
     */
    private void growPopulation() {
        double populationFactor = 1 + state.getGrowthFactor();
        double newPopulation = state.getPopulation() * populationFactor;
        state.setPopulation(Math.round(newPopulation));
    }

    /**
     * Calculate the disease spread by travelling people.
     */
    private void spreadDiseasesTravelling(TravelRoute route) {
        for (InfectionState i : state.getInfections())
            spreadDiseaseTravelling(route, i);
    }

    private void spreadDiseaseTravelling(TravelRoute route, InfectionState i) {
        double infectedTravellers = route.getTravelersPerTick() * calculateInfectedPercentage(i);
        long infectedTravellersRounded;
        if (infectedTravellers < 1.0)
            infectedTravellersRounded = checkChanceFor1InfectedTraveller(infectedTravellers);
        else
            infectedTravellersRounded = Math.round(infectedTravellers);

        CountryState targetCountry = route.getTargetCountry(this.state);
        targetCountry.addInfected(i.getDisease(), infectedTravellersRounded);
        i.increaseAmount(-1 * infectedTravellersRounded);
    }

    private double calculateInfectedPercentage(InfectionState i) {
        long infectedAmount = i.getAmount();
        return calculateInfectedPercentage(infectedAmount);
    }

    private long checkChanceFor1InfectedTraveller(double infectedTravellers) {
        if (Math.random() < infectedTravellers)
            return 1;
        else
            return 0;
    }

    /**
     * Calculate the disease spread by disease spread rate.
     */
    private void applyDiseaseToPopulation(InfectionState i) {
        long killedPeople = killPeople(i);

        long additionallyInfectedPeople = infectPeople(i);
        i.increaseAmount(additionallyInfectedPeople - killedPeople);
    }

    /**
     * Returns the infected percentage of the population.
     * As a percentage, the value is always between 0 and 1.
     */
    private double calculateInfectedPercentage(double infectedPeople) {
        double temp = infectedPeople / (double) state.getPopulation();
        if (temp < 0) return 0;
        else if (1 < temp) return 1;
        else return temp;
    }


    /**
     * With the given probability, return 1, else 0.
     */
    private long oneByChance(double probability) {
        double randomNumber = Math.random();
        if (randomNumber < probability)
            return 1;
        return 0;
    }

    private long killPeople(InfectionState infection) {
        long population = state.getPopulation();
        double lethalityFactor = infection.getDisease().getLethalityFactor();

        long killedPeople = applyFactor(infection, population, lethalityFactor);

        updateOtherInfections(infection, population, killedPeople);
        updatePopulation(population, killedPeople);

        return killedPeople;
    }

    /**
     * For every other Disease, kill the same percentage of infected as were killed of the total population.
     */
    private void updateOtherInfections(InfectionState infection, double population, long killedPeople) {
        double killedPercentage = killedPeople / population;
        state.getInfections()
                .stream()
                .filter(i -> i != infection)
                .forEach(i -> i.reduceByFactor(killedPercentage));
    }

    private void updatePopulation(long population, long killedPeople) {
        state.setPopulation(population - killedPeople);
        state.increaseDeceasedPopulation(killedPeople);
    }

    /**
     * Get the product of the factor and the amount of infected people, up to the given maximum.
     */
    private long applyFactor(InfectionState i, long max, double factor) {
        long killedPeople = Math.round(i.getAmount() * factor);
        if (killedPeople == 0) {
            killedPeople = oneByChance(factor);
        }

        killedPeople = Math.min(killedPeople, max);
        return killedPeople;
    }


    private long infectPeople(InfectionState i) {
        long healthyPeople = state.getPopulation() - i.getAmount();
        double spreadRate = i.getDisease().getSpreadRate();

        long additionallyInfectedPeople = applyFactor(i, healthyPeople, spreadRate);

        return additionallyInfectedPeople;
    }

    public long getInfectedPeople(DiseaseState disease) {
        for (InfectionState i : state.getInfections())
            if (i.getDisease().equals(disease))
                return i.getAmount();
        return 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("Name", state.getName())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        return new EqualsBuilder()
                .append(this.state.getName(), country.getState().getName())
                .isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.getState().getName())
                .build();
    }

    public CountryState getState() {
        return state;
    }

    public void setState(CountryState state) {
        this.state = state;
    }
}
package de.belmega.biohazard.core.country;


import de.belmega.biohazard.core.disease.Disease;
import de.belmega.biohazard.server.persistence.state.CountryState;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Country {

    private Map<Disease, Double> infectedPercentagePerDisease = new HashMap<>();

    private CountryState state;


    public Country(CountryState countryState) {
        this.state = countryState;
    }


    public void tick() {

        state.getRoutes().forEach(this::spreadDiseasesTraveling);

        double populationFactor = 1 + state.getGrowthFactor();
        double newPopulation = state.getPopulation() * populationFactor;
        state.setPopulation(Math.round(newPopulation));

        infectedPercentagePerDisease.keySet().forEach(this::applyDiseaseToPopulation);
    }

    private void spreadDiseasesTraveling(TravelRoute route) {
        for (Disease d : infectedPercentagePerDisease.keySet()) {
            double infectedTravellers = route.getTravelersPerTick() * infectedPercentagePerDisease.get(d);
            long infectedTravellersRounded;
            if (infectedTravellers < 1.0)
                infectedTravellersRounded = checkChanceFor1InfectedTraveller(infectedTravellers);
            else
                infectedTravellersRounded = Math.round(infectedTravellers);

            CountryState targetCountry = route.getTargetCountry(this.state);
            targetCountry.addInfected(d.getName(), infectedTravellersRounded);
            this.state.addInfected(d.getName(), -1 * infectedTravellersRounded);
        }
    }

    private long checkChanceFor1InfectedTraveller(double infectedTravellers) {
        if (Math.random() < infectedTravellers)
            return 1;
        else
            return 0;
    }

    private void applyDiseaseToPopulation(Disease d) {
        long infectedPeople = Math.round(infectedPercentagePerDisease.get(d) * state.getPopulation());

        long killedPeople = calculateKilledPeople(d, infectedPeople);
        long additionallyInfectedPeople = calculateAdditionallyInfectedPeople(d, infectedPeople);

        infectedPeople = infectedPeople + additionallyInfectedPeople - killedPeople;
        double infectedPercentage = calculateInfectedPercentage(infectedPeople);
        infectedPercentagePerDisease.put(d, infectedPercentage);
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

    private long calculateAdditionallyInfectedPeople(Disease d, long infectedPeople) {
        long additionallyInfectedPeople = Math.round(infectedPeople * (d.getSpreadRate()));
        if (additionallyInfectedPeople == 0) {
            double randomNumber = Math.random();
            if (randomNumber < d.getSpreadRate()) {
                additionallyInfectedPeople++;
            }
        }
        return additionallyInfectedPeople;
    }

    private long calculateKilledPeople(Disease d, long infectedPeople) {
        long killedPeople = Math.round(infectedPeople * d.getLethalityFactor());
        if (killedPeople == 0) {
            double randomNumber = Math.random();
            if (randomNumber < d.getLethalityFactor()) {
                killedPeople++;
            }
        }
        state.setPopulation(state.getPopulation() - killedPeople);
        state.setDeceasedPopulation(state.getDeceasedPopulation() + killedPeople);
        return killedPeople;
    }

    public void add(Disease disease, long amount) {
        double percentage = calculateInfectedPercentage(amount);
        this.infectedPercentagePerDisease.put(disease, percentage);
    }


    public long getInfectedPeople(Disease disease) {
        Double percentage = this.infectedPercentagePerDisease.get(disease);
        return Math.round(percentage * state.getPopulation());
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

    public Set<Disease> getDiseases() {
        return infectedPercentagePerDisease.keySet();
    }

    public CountryState getState() {
        return state;
    }

    public void setState(CountryState state) {
        this.state = state;
    }
}
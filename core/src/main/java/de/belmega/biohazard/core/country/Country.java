package de.belmega.biohazard.core.country;


import de.belmega.biohazard.core.disease.Disease;

import java.util.HashMap;
import java.util.Map;

public class Country {

    private long population;

    private double populationGrowthFactor = 0;

    private Map<Disease, Double> infectedPercentagePerDisease = new HashMap<>();

    public Country(long initialPopulation) {
        population = initialPopulation;
    }

    public void tick() {
        population = Math.round(population * (1 + populationGrowthFactor));

        for (Disease d : infectedPercentagePerDisease.keySet()) {
            long infectedPeople = Math.round(infectedPercentagePerDisease.get(d) * population);

            long killedPeople = calculateKilledPeople(d, infectedPeople);
            long additionallyInfectedPeople = calculateAdditionallyInfectedPeople(d, infectedPeople);

            infectedPeople = infectedPeople + additionallyInfectedPeople - killedPeople;
            double infectedPercentage = calculateInfectedPercentage(infectedPeople);
            infectedPercentagePerDisease.put(d, infectedPercentage);
        }
    }

    /**
     * Returns the infected percentage of the population.
     * As a percentage, the value is always between 0 and 1.
     */
    private double calculateInfectedPercentage(double infectedPeople) {
        double temp = infectedPeople / (double) population;
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
        population -= killedPeople;
        return killedPeople;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulationGrowthFactor(double populationGrowthFactor) {
        this.populationGrowthFactor = populationGrowthFactor;
    }

    public void add(Disease disease, long amount) {
        double percentage = calculateInfectedPercentage(amount);
        this.infectedPercentagePerDisease.put(disease, percentage);
    }

    public long getInfectedPeople(Disease disease) {
        Double percentage = this.infectedPercentagePerDisease.get(disease);
        return Math.round(percentage * population);
    }
}
package de.belmega.biohazard.core.country;


import de.belmega.biohazard.core.disease.Disease;

import java.util.HashMap;
import java.util.Map;

public class Country {

    private long population;
    /**
     * Standard population growth per tick is 1% divided by 365,
     * simulating 1% yearly population growth for a 1-day-tick.
     */
    private double populationGrowthFactor = 0.01 / 365;

    private Map<Disease, Long> diseases = new HashMap<>();

    public Country(long initialPopulation) {
        population = initialPopulation;
    }

    public void tick() {
        population = Math.round(population * (1 + populationGrowthFactor));

        for (Disease d : diseases.keySet()) {
            long infectedPeople = diseases.get(d);
            long additionallyInfectedPeople = Math.round(infectedPeople * (d.getSpreadRate()));
            if (additionallyInfectedPeople == 0) {
                double randomNumber = Math.random();
                if (randomNumber < d.getSpreadRate()) {
                    additionallyInfectedPeople++;
                }
            }
            infectedPeople = Math.min(population, infectedPeople + additionallyInfectedPeople);
            diseases.put(d, infectedPeople);
        }
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulationGrowthFactor(double populationGrowthFactor) {
        this.populationGrowthFactor = populationGrowthFactor;
    }

    public void add(Disease disease, long amount) {
        this.diseases.put(disease, amount);
    }

    public long getInfectedPeople(Disease disease) {
        return this.diseases.get(disease);
    }
}

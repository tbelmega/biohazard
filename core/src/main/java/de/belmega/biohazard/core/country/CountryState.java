package de.belmega.biohazard.core.country;

import java.util.HashMap;
import java.util.Map;

/**
 * A CountryState represents the state of a Country at a specific point in time.
 * Thus a Country can dump it's state into a CountryState and be restored from it.
 */
public class CountryState {
    private final String name;
    private long population;
    private double growthFactor;
    private Map<String, Long> infectedPeoplePerDisease = new HashMap<>();

    public CountryState(String name) {
        this.name = name;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public double getGrowthFactor() {
        return growthFactor;
    }

    public void setGrowthFactor(double growthFactor) {
        this.growthFactor = growthFactor;
    }

    public Map<String, Long> getInfectedPeoplePerDisease() {
        return infectedPeoplePerDisease;
    }

    public void add(String name, Long infectedPeople) {
        this.infectedPeoplePerDisease.put(name, infectedPeople);
    }

    public void addInfected(String name, long infectedPeople) {
        this.infectedPeoplePerDisease.put(name, infectedPeople);
    }

    public String getName() {
        return name;
    }
}

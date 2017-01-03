package de.belmega.biohazard.server.persistence;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A CountryState represents the state of a Country at a specific point in time.
 * Thus a Country can dump it's state into a CountryState and be restored from it.
 */
@Entity
public class CountryState extends NamedGameEntityState {

    @Id
    private String name;

    @ManyToOne
    private ContinentState continent;

    private long population;
    private double growthFactor;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<InfectionState> infectedPeoplePerDisease = new HashSet<>();

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

    public Collection<InfectionState> getInfectedPeoplePerDisease() {
        return infectedPeoplePerDisease;
    }

    public void setInfectedPeoplePerDisease(Set<InfectionState> infectedPeoplePerDisease) {
        this.infectedPeoplePerDisease = infectedPeoplePerDisease;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContinentState getContinent() {
        return continent;
    }

    public void setContinent(ContinentState continent) {
        this.continent = continent;
    }

    public void addInfected(String diseaseName, long infectedPeople) {
        this.infectedPeoplePerDisease.add(new InfectionState(this, diseaseName, infectedPeople));
    }
}

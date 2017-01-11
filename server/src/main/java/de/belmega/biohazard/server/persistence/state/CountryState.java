package de.belmega.biohazard.server.persistence.state;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.core.disease.Disease;
import de.belmega.biohazard.core.world.World;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "continent", nullable = false)
    private ContinentState continent;

    private long population;

    /**
     * The growth per tick. A growth factor of 0 represents stagnation,
     * a growth factor of 1 represents a 100% growth per tick (doubling the population).
     */
    private double growthFactor;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<InfectionState> infectedPeoplePerDisease = new HashSet<>();
    private long deceasedPopulation;

    public CountryState(String name, long population) {
        this.name = name;
        this.population = population;
    }

    public CountryState() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

    public Collection<InfectionState> getInfectedPeoplePerDisease() {
        return infectedPeoplePerDisease;
    }

    public void setInfectedPeoplePerDisease(Set<InfectionState> infectedPeoplePerDisease) {
        this.infectedPeoplePerDisease = infectedPeoplePerDisease;
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

    public Country build(World world) {
        Country country = new Country(this);

        for (InfectionState infection : this.getInfectedPeoplePerDisease()) {
            Disease d = world.getDiseaseByName(infection.getDiseaseName());
            country.add(d, infection.getAmount());
        }

        return country;
    }

    public long getDeceasedPopulation() {
        return deceasedPopulation;
    }

    public void setDeceasedPopulation(long deceasedPopulation) {
        this.deceasedPopulation = deceasedPopulation;
    }

    public void add(DiseaseState avianFlu, long l) {

    }
}

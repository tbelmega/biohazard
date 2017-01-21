package de.belmega.biohazard.server.persistence.state;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.core.country.TravelRoute;

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
    private Set<InfectionState> infections = new HashSet<>();

    private long deceasedPopulation;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "CountryState_TravelRoute",
            joinColumns = {@JoinColumn(name = "countryState")},
            inverseJoinColumns = {@JoinColumn(name = "travelRoute")}
    )
    private Set<TravelRoute> routes = new HashSet<>();

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

    public Collection<InfectionState> getInfections() {
        return infections;
    }

    public ContinentState getContinent() {
        return continent;
    }

    public void setContinent(ContinentState continent) {
        this.continent = continent;
    }

    public void add(InfectionState infectionState) {
        this.infections.add(infectionState);
    }

    public Country build() {
        return new Country(this);
    }

    public long getDeceasedPopulation() {
        return deceasedPopulation;
    }

    public void setDeceasedPopulation(long deceasedPopulation) {
        this.deceasedPopulation = deceasedPopulation;
    }

    public Set<TravelRoute> getRoutes() {
        return routes;
    }

    public void addRoute(TravelRoute route) {
        this.routes.add(route);
    }

    public void addInfected(DiseaseState disease, long amount) {
        InfectionState infection = null;
        for (InfectionState i : this.infections)
            if (i.getDisease().equals(disease))
                infection = i;
        if (infection != null)
            infection.increaseAmount(amount);
        else
            InfectionState.create(this, disease, amount);
    }

    public void increaseDeceasedPopulation(long killedPeople) {
        this.deceasedPopulation += killedPeople;
    }
}

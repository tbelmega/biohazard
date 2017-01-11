package de.belmega.biohazard.server.persistence.state;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.core.world.Continent;
import de.belmega.biohazard.core.world.World;

import javax.persistence.*;
import java.util.*;

@Entity
public class ContinentState extends NamedGameEntityState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "world", nullable = false)
    private WorldState world;

    @OneToMany(mappedBy = "continent", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CountryState> countries = new HashSet<>();

    public ContinentState(String name) {
        this.name = name;
    }

    public ContinentState() {
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


    public Set<CountryState> getCountries() {
        return countries;
    }

    public void addCountry(CountryState countryState) {
        countryState.setContinent(this);
        this.countries.add(countryState);
    }

    public void addCountry(Country country) {
        addCountry(country.getState());
    }

    public WorldState getWorld() {
        return world;
    }

    public void setWorld(WorldState world) {
        this.world = world;
    }

    public Continent build(World world) {
        Continent continent = new Continent(this);
        for (CountryState countryState : this.getCountries())
            continent.add(countryState.build(world));
        return continent;
    }

    @Transient
    public List<CountryState> getSortedCountries() {
        List<CountryState> countries = new ArrayList<>(this.countries);
        Collections.sort(countries);
        return countries;
    }


    public void add(CountryState... countries) {
        for (CountryState c : countries)
            addCountry(c);
    }
}

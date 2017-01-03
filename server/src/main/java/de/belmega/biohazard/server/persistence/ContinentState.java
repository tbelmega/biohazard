package de.belmega.biohazard.server.persistence;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.core.world.Continent;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ContinentState extends NamedGameEntityState {

    @Id
    private String name;

    @ManyToOne
    @JoinColumn(name = "world", nullable = false)
    private WorldState world;

    @OneToMany(mappedBy = "continent", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CountryState> countries = new HashSet<>();

    public static ContinentState getState(Continent continent) {
        ContinentState continentState = new ContinentState();
        continentState.setName(continent.getName());
        for (Country c : continent.getCountries())
            continentState.addCountry(CountryState.getState(c));
        return continentState;
    }

    public Set<CountryState> getCountries() {
        return countries;
    }

    public void addCountry(CountryState countryState) {
        countryState.setContinent(this);
        this.countries.add(countryState);
    }

    public void addCountry(Country foo) {
        addCountry(CountryState.getState(foo));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WorldState getWorld() {
        return world;
    }

    public void setWorld(WorldState world) {
        this.world = world;
    }

    public Continent build() {
        Continent continent = new Continent(this.getName());
        for (CountryState countryState : this.getCountries())
            continent.add(countryState.build());
        return continent;
    }


}

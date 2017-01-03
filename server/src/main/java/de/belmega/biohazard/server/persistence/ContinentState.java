package de.belmega.biohazard.server.persistence;

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

    public Set<CountryState> getCountries() {
        return countries;
    }

    public void addCountry(CountryState countryState) {
        countryState.setContinent(this);
        this.countries.add(countryState);
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
}

package de.belmega.biohazard.server.persistence;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class ContinentState extends NamedGameEntityState {

    @Id
    private String name;

    @ManyToOne
    private WorldState world;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<CountryState> countries = new HashSet<>();

    public Collection<CountryState> getCountries() {
        return countries;
    }

    public void addCountry(CountryState countryState) {
        this.countries.add(countryState);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

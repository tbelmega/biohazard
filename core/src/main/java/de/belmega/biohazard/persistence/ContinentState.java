package de.belmega.biohazard.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class ContinentState extends NamedGameEntityState {

    @Id
    private String name;

    @ManyToOne
    private WorldState world;

    @OneToMany
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

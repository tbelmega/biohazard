package de.belmega.biohazard.core.world;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.server.persistence.ContinentState;
import de.belmega.biohazard.server.persistence.CountryState;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Continent {
    private final String name;
    private Map<String, Country> countries = new HashMap<>();

    public Continent(String name) {
        this.name = name;
    }

    public static Continent build(ContinentState continentState) {
        Continent continent = new Continent(continentState.getName());
        for (CountryState countryState : continentState.getCountries())
            continent.add(Country.build(countryState));
        return continent;
    }

    public void tick() {
        this.countries.values().forEach(Country::tick);
    }

    public void add(Country... countries) {
        for (Country country : countries)
            this.countries.put(country.getName(), country);
    }

    public Set<Country> getCountries() {
        return new HashSet<>(countries.values());
    }

    public ContinentState getState() {
        ContinentState continentState = new ContinentState();
        continentState.setName(this.getName());
        for (Country c : this.getCountries())
            continentState.addCountry(c.getState());
        return continentState;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("Name", name)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Continent continent = (Continent) o;

        return new EqualsBuilder()
                .append(this.name, continent.name)
                .isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.name)
                .build();
    }

    public Country getCountry(String name) {
        Country country = this.countries.get(name);
        return country;
    }
}
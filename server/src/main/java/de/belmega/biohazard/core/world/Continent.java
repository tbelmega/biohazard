package de.belmega.biohazard.core.world;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.server.persistence.state.ContinentState;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Continent {

    private Map<String, Country> countries = new HashMap<>();

    private ContinentState state;

    public Continent(ContinentState state) {
        this.state = state;
    }


    public void tick() {
        this.countries.values().forEach(Country::tick);
    }

    public void add(Country... countries) {
        for (Country country : countries) {
            state.addCountry(country);
            this.countries.put(country.getState().getName(), country);
        }
    }

    public Set<Country> getCountries() {
        return new HashSet<>(countries.values());
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("Name", state.getName())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Continent continent = (Continent) o;

        return new EqualsBuilder()
                .append(state.getName(), continent.getState().getName())
                .isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getState().getName())
                .build();
    }

    public Country getCountry(String name) {
        Country country = this.countries.get(name);
        return country;
    }

    public ContinentState getState() {
        return state;
    }

    public void setState(ContinentState state) {
        this.state = state;
    }
}

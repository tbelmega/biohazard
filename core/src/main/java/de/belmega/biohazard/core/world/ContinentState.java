package de.belmega.biohazard.core.world;

import java.util.HashSet;
import java.util.Set;

public class ContinentState {
    private Set<String> countries = new HashSet<>();

    public Set<String> getCountries() {
        return countries;
    }

    public void setCountries(Set<String> countries) {
        this.countries = countries;
    }

    public void addCountry(String name) {
        this.countries.add(name);
    }
}

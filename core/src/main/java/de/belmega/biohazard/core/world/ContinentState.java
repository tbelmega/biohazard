package de.belmega.biohazard.core.world;

import de.belmega.biohazard.core.country.CountryState;

import java.util.HashMap;
import java.util.Map;

public class ContinentState {
    private final String name;
    private Map<String, CountryState> countries = new HashMap<>();

    public ContinentState(String name) {
        this.name = name;
    }

    public Map<String, CountryState> getCountries() {
        return countries;
    }

    public void addCountry(CountryState countryState) {
        this.countries.put(countryState.getName(), countryState);
    }

    public String getName() {
        return name;
    }
}

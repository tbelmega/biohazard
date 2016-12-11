package de.belmega.biohazard.core.world;

import de.belmega.biohazard.core.country.Country;

import java.util.HashSet;
import java.util.Set;

public class Continent {
    private Set<Country> countries = new HashSet<>();

    public void tick() {
        this.countries.forEach(Country::tick);
    }

    public void add(Country... countries) {
        for (Country country : countries) {
            this.countries.add(country);
        }
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public ContinentState getState() {
        ContinentState continentState = new ContinentState();
        for (Country c : this.getCountries()) {
            continentState.addCountry(c.getName());
        }
        return continentState;
    }
}

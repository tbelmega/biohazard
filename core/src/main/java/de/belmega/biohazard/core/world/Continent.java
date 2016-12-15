package de.belmega.biohazard.core.world;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.core.country.CountryState;

import java.util.HashSet;
import java.util.Set;

public class Continent {
    private final String name;
    private Set<Country> countries = new HashSet<>();

    public Continent(String name) {
        this.name = name;
    }

    public static Continent build(ContinentState continentState) {
        Continent continent = new Continent(continentState.getName());
        for (CountryState countryState : continentState.getCountries().values())
            continent.add(Country.build(countryState));
        return continent;
    }

    public void tick() {
        this.countries.forEach(Country::tick);
    }

    public void add(Country... countries) {
        for (Country country : countries)
            this.countries.add(country);
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public ContinentState getState() {
        ContinentState continentState = new ContinentState(this.getName());
        for (Country c : this.getCountries())
            continentState.addCountry(c.getState());
        return continentState;
    }

    public String getName() {
        return name;
    }
}

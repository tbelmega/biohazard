package de.belmega.biohazard.core.world;

import de.belmega.biohazard.core.country.Country;

import java.util.UUID;

public class WorldGenerator {

    private final WorldGenerationParams params;
    private World world;

    public WorldGenerator(WorldGenerationParams params) {
        this.params = params;
    }

    public World generate() {
        this.world = new World();
        world.add(generateContinents());
        return world;
    }

    private Continent[] generateContinents() {
        int range = params.getMaxContinents() - params.getMinContinents();

        if (range < 0) throw new IllegalArgumentException(
                "Maximal number of continents must be higher than minimal number of continents.");

        int numberOfContinents = params.getMinContinents() + (int) (Math.random() * range);
        Continent[] continents = new Continent[numberOfContinents];

        for (int i = 0; i < numberOfContinents; i++)
            continents[i] = generateContinent();

        return continents;
    }

    private Continent generateContinent() {
        Continent continent = new Continent("Continent_" + UUID.randomUUID().getMostSignificantBits());
        continent.add(generateCountries());
        return continent;
    }

    private Country[] generateCountries() {
        int range = params.getMaxCountriesPerContinent() - params.getMinCountriesPerContinent();

        if (range < 0) throw new IllegalArgumentException(
                "Maximal number of countries must be higher than minimal number of countries.");

        int numberOfCountries = params.getMinCountriesPerContinent() + (int) (Math.random() * range);
        Country[] countries = new Country[numberOfCountries];

        for (int i = 0; i < numberOfCountries; i++)
            countries[i] = generateCountry();

        return countries;
    }

    Country generateCountry() {
        long minPopulation = params.getSqrtMinPopulationPerCountry();
        long range = params.getSqrtMaxPopulationPerCountry() - minPopulation;
        long factor1 = minPopulation + (int) (Math.random() * range);
        long factor2 = minPopulation + (int) (Math.random() * range);
        long population = factor1 * factor2;
        return new Country("Country_" + UUID.randomUUID().getMostSignificantBits(), population);
    }
}

package de.belmega.biohazard.core.world;

public class WorldGenerationParams {
    private int minContinents = 5;
    private int maxContinents = 7;

    private int minCountriesPerContinent = 5;
    private int maxCountriesPerContinent = 10;

    private long minSqrtPopulationPerCountry = 300;
    private long maxSqrtPopulationPerCountry = 40000;

    public int getMaxContinents() {
        return maxContinents;
    }

    public void setMaxContinents(int maxContinents) {
        this.maxContinents = maxContinents;
    }

    public int getMinContinents() {
        return minContinents;
    }

    public void setMinContinents(int minContinents) {
        this.minContinents = minContinents;
    }

    public int getMaxCountriesPerContinent() {
        return maxCountriesPerContinent;
    }

    public void setMaxCountriesPerContinent(int maxCountriesPerContinent) {
        this.maxCountriesPerContinent = maxCountriesPerContinent;
    }

    public int getMinCountriesPerContinent() {
        return minCountriesPerContinent;
    }

    public void setMinCountriesPerContinent(int minCountriesPerContinent) {
        this.minCountriesPerContinent = minCountriesPerContinent;
    }

    public long getSqrtMinPopulationPerCountry() {
        return minSqrtPopulationPerCountry;
    }

    public long getSqrtMaxPopulationPerCountry() {
        return maxSqrtPopulationPerCountry;
    }
}

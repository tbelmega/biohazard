package de.belmega.biohazard.core.world;

import de.belmega.biohazard.core.country.Country;

import java.util.HashSet;
import java.util.Set;

public class World {

    private Set<Continent> continents = new HashSet<>();
    private WorldRunner worldRunner;


    public void run(long millisecondsPerTick) {
        worldRunner = new WorldRunner(this, millisecondsPerTick);
        Thread worldThread = new Thread(worldRunner);

        worldThread.start();
    }

    public void add(Continent... continents) {
        for (Continent continent : continents) {
            this.continents.add(continent);
        }
    }

    public void stop() {
        worldRunner.stop();
    }

    public void tick() {
        continents.forEach(Continent::tick);
    }


    public Set<Continent> getContinents() {
        return continents;
    }

    public Set<Country> getCountries() {
        final Set<Country> countries = new HashSet<>();
        for (Continent c : continents) {
            countries.addAll(c.getCountries());
        }
        return countries;
    }
}

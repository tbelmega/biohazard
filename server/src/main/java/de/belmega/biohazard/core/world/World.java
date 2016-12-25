package de.belmega.biohazard.core.world;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.server.persistence.ContinentState;
import de.belmega.biohazard.server.persistence.WorldState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class World {

    private Map<String, Continent> continents = new HashMap<>();
    private WorldRunner worldRunner;

    public static World build(WorldState worldState) {
        World world = new World();
        for (ContinentState c : worldState.getContinents())
            world.add(Continent.build(c));
        return world;
    }

    public void run(long millisecondsPerTick) {
        worldRunner = new WorldRunner(this, millisecondsPerTick);
        Thread worldThread = new Thread(worldRunner);

        worldThread.start();
    }

    public void add(Continent... continents) {
        for (Continent continent : continents) {
            this.continents.put(continent.getName(), continent);
        }
    }

    public void stop() {
        worldRunner.stop();
    }

    public void tick() {
        continents.values().forEach(Continent::tick);
    }

    public Set<Continent> getContinents() {
        return new HashSet<>(continents.values());
    }

    public Set<Country> getCountries() {
        final Set<Country> countries = new HashSet<>();
        for (Continent c : continents.values())
            countries.addAll(c.getCountries());
        return countries;
    }

    public WorldState getState() {
        WorldState worldState = new WorldState();
        for (Continent c : continents.values())
            worldState.addContinent(c.getState());
        return worldState;
    }

    public Continent getContinent(String name) {
        return this.continents.get(name);
    }
}

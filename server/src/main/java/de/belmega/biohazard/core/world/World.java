package de.belmega.biohazard.core.world;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.core.disease.Disease;
import de.belmega.biohazard.server.persistence.ContinentState;
import de.belmega.biohazard.server.persistence.WorldState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class World {

    private WorldRunner worldRunner;

    private Map<String, Continent> continents = new HashMap<>();
    private Map<String, Disease> diseases = new HashMap<>();

    private long age;

    public World() {
        age = 0L;
    }

    public static World build(WorldState worldState) {
        World world = new World();
        for (ContinentState c : worldState.getContinents())
            world.add(Continent.build(c));
        world.setAge(worldState.getAge());
        return world;
    }

    public void run(long millisecondsPerTick) {
        worldRunner = new WorldRunner(this, millisecondsPerTick);
        Thread worldThread = new Thread(worldRunner);

        worldThread.start();
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public void add(Continent... continents) {
        for (Continent continent : continents)
            this.continents.put(continent.getName(), continent);
    }

    public void add(Disease... diseases) {
        for (Disease d : diseases)
            this.diseases.put(d.getName(), d);
    }

    public void stop() {
        worldRunner.stop();
    }

    public void tick() {
        this.age += 1;
        continents.values().forEach(Continent::tick);
    }

    public Set<Continent> getContinents() {
        return new HashSet<>(continents.values());
    }

    public Set<Country> getCountries() {
        Set<Country> countries = new HashSet<>();
        for (Continent c : continents.values())
            countries.addAll(c.getCountries());
        return countries;
    }

    public WorldState getState() {
        WorldState worldState = new WorldState();
        worldState.setAge(age);
        for (Continent c : continents.values())
            worldState.addContinent(c.getState());
        for (Disease d : diseases.values())
            worldState.addDisease(d.getState());
        return worldState;
    }

    public Continent getContinent(String name) {
        return this.continents.get(name);
    }


}

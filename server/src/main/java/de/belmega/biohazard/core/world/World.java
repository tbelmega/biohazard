package de.belmega.biohazard.core.world;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.core.disease.Disease;

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


    public Continent getContinent(String name) {
        return this.continents.get(name);
    }

    public Set<Disease> getDiseases() {
        return new HashSet<>(diseases.values());
    }
}

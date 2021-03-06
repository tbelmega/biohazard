package de.belmega.biohazard.core.world;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.server.persistence.state.DiseaseState;
import de.belmega.biohazard.server.persistence.state.WorldState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class World {

    public static final World NO_WORLD = new NullWorld(new WorldState());

    private WorldRunner worldRunner;

    private WorldState state;

    private Map<String, Continent> continents = new HashMap<>();
    private Map<String, DiseaseState> diseases = new HashMap<>();


    public World(WorldState state) {
        this.state = state;
    }


    public void run(long millisecondsPerTick) {
        worldRunner = new WorldRunner(this, millisecondsPerTick);
        Thread worldThread = new Thread(worldRunner);

        worldThread.start();
    }


    public void add(Continent... continents) {
        for (Continent continent : continents)
            this.continents.put(continent.getState().getName(), continent);
    }

    public void add(DiseaseState... diseases) {
        for (DiseaseState d : diseases)
            this.diseases.put(d.getName(), d);
    }

    public void stop() {
        worldRunner.stop();
    }

    public void tick() {
        this.state.setAge(this.state.getAge() + 1);
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

    public Set<DiseaseState> getDiseases() {
        return new HashSet<>(diseases.values());
    }

    public DiseaseState getDiseaseByName(String diseaseName) {
        DiseaseState disease = diseases.get(diseaseName);
        if (disease != null) return disease;
        else throw new IllegalArgumentException("Disease does not exist.");
    }

    public WorldState getState() {
        return state;
    }


    /**
     * NullObjectPattern. Placeholder for state "no World object" without need for null checks.
     */
    private static class NullWorld extends World {
        public NullWorld(WorldState state) {
            super(state);
        }

        @Override
        public DiseaseState getDiseaseByName(String diseaseName) {
            return new DiseaseState(diseaseName, 0.0);
        }

    }
}

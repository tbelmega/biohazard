package de.belmega.biohazard.server.persistence;

import de.belmega.biohazard.core.disease.Disease;
import de.belmega.biohazard.core.world.Continent;
import de.belmega.biohazard.core.world.World;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tbelmega on 17.12.2016.
 */
@Entity
public class WorldState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @OneToMany(mappedBy = "world", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ContinentState> continents = new HashSet<>();

    @OneToMany(mappedBy = "world", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<DiseaseState> diseases = new HashSet<>();

    private long age;

    public static WorldState getState(World w) {
        WorldState worldState = new WorldState();
        worldState.setAge(w.getAge());
        w.getContinents().forEach(worldState::addContinent);
        w.getDiseases().forEach(worldState::addDisease);
        return worldState;
    }

    public Set<ContinentState> getContinents() {
        return continents;
    }

    public void setContinents(Set<ContinentState> continents) {
        this.continents = continents;
    }

    public void addContinent(ContinentState continentState) {
        continentState.setWorld(this);
        this.continents.add(continentState);
    }

    public Set<DiseaseState> getDiseases() {
        return diseases;
    }

    public void addDisease(Disease d) {
        DiseaseState state = DiseaseState.getState(d);
        addDisease(state);
    }

    public void addDisease(DiseaseState disease) {
        disease.setWorld(this);
        this.diseases.add(disease);
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public void addContinent(Continent c) {
        addContinent(ContinentState.getState(c));
    }


    public World build() {
        World world = new World();
        for (ContinentState c : this.getContinents())
            world.add(c.build());
        for (DiseaseState d : this.getDiseases())
            world.add(DiseaseState.build(d));
        world.setAge(this.getAge());
        return world;
    }
}

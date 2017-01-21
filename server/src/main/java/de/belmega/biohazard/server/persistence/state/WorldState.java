package de.belmega.biohazard.server.persistence.state;

import de.belmega.biohazard.core.world.World;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class WorldState extends NamedGameEntityState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "world", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ContinentState> continents = new HashSet<>();

    @OneToMany(mappedBy = "world", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<DiseaseState> diseases = new HashSet<>();


    private long age;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<ContinentState> getContinents() {
        return continents;
    }

    public void addContinent(ContinentState continentState) {
        continentState.setWorld(this);
        this.continents.add(continentState);
    }

    public Set<DiseaseState> getDiseases() {
        return diseases;
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

    public World build() {
        World world = new World(this);
        for (DiseaseState d : this.getDiseases())
            world.add(d);
        for (ContinentState c : this.getContinents())
            world.add(c.build(world));

        return world;
    }

    public void add(ContinentState... continentStates) {
        for (ContinentState c : continentStates)
            addContinent(c);
    }

    public void add(DiseaseState... diseaseStates) {
        for (DiseaseState d : diseaseStates)
            addDisease(d);
    }

}

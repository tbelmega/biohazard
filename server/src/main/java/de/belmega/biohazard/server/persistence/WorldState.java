package de.belmega.biohazard.server.persistence;

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
}

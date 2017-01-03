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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ContinentState> continents = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<DiseaseState> diseases = new HashSet<>();

    public Set<ContinentState> getContinents() {
        return continents;
    }

    public void setContinents(Set<ContinentState> continents) {
        this.continents = continents;
    }

    public void addContinent(ContinentState continentState) {
        this.continents.add(continentState);
    }

    public Set<DiseaseState> getDiseases() {
        return diseases;
    }

    public void addDisease(DiseaseState disease) {
        this.diseases.add(disease);
    }
}

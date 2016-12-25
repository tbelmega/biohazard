package de.belmega.biohazard.server.persistence;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author tbelmega on 17.12.2016.
 */
@Entity
public class WorldState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<ContinentState> continents = new HashSet<>();

    public Collection<ContinentState> getContinents() {
        return continents;
    }

    public void addContinent(ContinentState continentState) {
        this.continents.add(continentState);
    }
}

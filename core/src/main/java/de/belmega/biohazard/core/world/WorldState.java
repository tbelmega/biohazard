package de.belmega.biohazard.core.world;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tbelmega on 17.12.2016.
 */
public class WorldState {
    private Map<String, ContinentState> continents = new HashMap<>();

    public Map<String, ContinentState> getContinents() {
        return continents;
    }

    public void addContinent(ContinentState continentState) {
        this.continents.put(continentState.getName(), continentState);
    }
}

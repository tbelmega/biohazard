package de.belmega.biohazard.server.jsf;

import de.belmega.biohazard.core.world.World;
import de.belmega.biohazard.server.ejb.WorldDAO;
import de.belmega.biohazard.server.ejb.WorldRunStatus;
import de.belmega.biohazard.server.persistence.entities.WorldEntity;
import de.belmega.biohazard.server.persistence.state.ContinentState;
import de.belmega.biohazard.server.persistence.state.CountryState;
import de.belmega.biohazard.server.persistence.state.InfectionState;
import de.belmega.biohazard.server.persistence.state.WorldState;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.util.*;

/**
 * @author tbelmega on 04.12.2016.
 */
@ManagedBean
@SessionScoped
public class WorldDetailsBean {
    @Inject
    WorldDAO worldDAO;
    private WorldEntity worldEntity;
    private long worldId;
    private WorldRunStatus status = WorldRunStatus.STOPPED;

    private World world;

    public void loadWorldEntity() {
        worldEntity = worldDAO.findWorld(worldId);
    }

    public long getWorldId() {
        return worldId;
    }

    public void setWorldId(long worldId) {
        this.worldId = worldId;
    }

    public WorldEntity getWorldEntity() {
        return worldEntity;
    }

    public void setWorldEntity(WorldEntity worldEntity) {
        this.worldEntity = worldEntity;
    }

    public String destroyWorldEntity() {
        worldDAO.destroyWorld(worldEntity);
        return "index";
    }

    public List<ContinentState> getContinents() {
        WorldState worldState = worldEntity.getWorldState();
        Set<ContinentState> continents = worldState.getContinents();
        List<ContinentState> continentList = new ArrayList<>(continents);
        Collections.sort(continentList);
        return continentList;
    }

    public Long getTotalPopulation() {
        long totalPopulation = 0;
        for (ContinentState c : getContinents())
            for (CountryState country : c.getCountries())
                totalPopulation += country.getPopulation();

        return totalPopulation;
    }

    public List<String> getDiseaseNames() {
        Set<String> diseaseNames = new HashSet<>();
        for (ContinentState continent : worldEntity.getWorldState().getContinents())
            for (CountryState c : continent.getCountries())
                for (InfectionState i : c.getInfectedPeoplePerDisease())
                    diseaseNames.add(i.getDiseaseName());

        List<String> diseaseNameList = new ArrayList<>(diseaseNames);
        Collections.sort(diseaseNameList);
        return diseaseNameList;
    }

    public Map<String, Long> getWorldInfectionState() {
        Map<String, Long> infections = new HashMap<>();

        for (ContinentState continent : worldEntity.getWorldState().getContinents())
            for (CountryState c : continent.getCountries())
                for (InfectionState i : c.getInfectedPeoplePerDisease())
                    if (infections.containsKey(i.getDiseaseName()))
                        infections.put(i.getDiseaseName(), infections.get(i.getDiseaseName()) + i.getAmount());
                    else
                        infections.put(i.getDiseaseName(), i.getAmount());

        return infections;
    }

    public WorldRunStatus getStatus() {
        return status;
    }

    public String getBtnRunValue() {
        if (status.equals(WorldRunStatus.RUNNING))
            return "Stop";
        else
            return "Run";
    }

    public String btnRunClick() {
        if (status.equals(WorldRunStatus.RUNNING))
            stopWorld();
        else
            startWorld();

        return "worlddetails.xhtml?worldId=" + worldId;
    }

    private void startWorld() {
        this.world = worldEntity.getWorldState().build();
        world.run(1000);

        status = WorldRunStatus.RUNNING;
    }

    private void stopWorld() {
        world.stop();
        worldEntity.setWorldState(WorldState.getState(world));
        worldDAO.saveWorld(worldEntity);

        status = WorldRunStatus.STOPPED;
    }

    public long getAge() {
        return worldEntity.getWorldState().getAge();
    }

    public String btnTickClick() {
        world = worldEntity.getWorldState().build();

        world.tick();

        worldEntity.setWorldState(WorldState.getState(world));
        worldDAO.saveWorld(worldEntity);

        return "worlddetails.xhtml?worldId=" + worldId;
    }

    public boolean isRunning() {
        return status.equals(WorldRunStatus.RUNNING);
    }
}

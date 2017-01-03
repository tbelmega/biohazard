package de.belmega.biohazard.server.jsf;

import de.belmega.biohazard.core.world.World;
import de.belmega.biohazard.server.ejb.WorldDAO;
import de.belmega.biohazard.server.ejb.WorldRunStatus;
import de.belmega.biohazard.server.persistence.entities.WorldEntity;
import de.belmega.biohazard.server.persistence.state.ContinentState;
import de.belmega.biohazard.server.persistence.state.CountryState;
import de.belmega.biohazard.server.persistence.state.DiseaseState;
import de.belmega.biohazard.server.persistence.state.WorldState;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Set;

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

    public Collection<ContinentState> getContinents() {
        return worldEntity.getWorldState().getContinents();
    }

    public Long getTotalPopulation() {
        long totalPopulation = 0;
        for (ContinentState c : getContinents())
            for (CountryState country : c.getCountries())
                totalPopulation += country.getPopulation();

        return totalPopulation;
    }

    public Set<DiseaseState> getDiseases() {
        return worldEntity.getWorldState().getDiseases();
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
}

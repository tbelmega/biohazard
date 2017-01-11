package de.belmega.biohazard.server.jsf;

import de.belmega.biohazard.server.ejb.WorldDAO;
import de.belmega.biohazard.server.persistence.entities.WorldSimulationEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

/**
 * @author tbelmega on 04.12.2016.
 */
@ManagedBean
@SessionScoped
public class CreateWorldBean {
    @Inject
    WorldDAO worldDAO;
    private WorldSimulationEntity worldSimulationEntity;

    public String destroyWorldEntity() {
        worldSimulationEntity = null;
        return "index";
    }

    public WorldSimulationEntity getWorldSimulationEntity() {
        if (worldSimulationEntity == null)
            worldSimulationEntity = new WorldSimulationEntity();
        return worldSimulationEntity;
    }

    public void setWorldSimulationEntity(WorldSimulationEntity worldSimulationEntity) {
        this.worldSimulationEntity = worldSimulationEntity;
    }

    public String submit() {
        worldDAO.saveWorld(getWorldSimulationEntity());
        worldSimulationEntity = null;
        return "index";
    }
}

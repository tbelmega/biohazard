package de.belmega.biohazard.jsf;

import de.belmega.biohazard.ejb.WorldDAO;
import de.belmega.biohazard.server.entities.WorldEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 * @author tbelmega on 04.12.2016.
 */
@ManagedBean
@RequestScoped
public class WorldDetailsBean {
    @Inject
    WorldDAO worldDAO;
    private WorldEntity worldEntity;
    private long worldId;

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
}

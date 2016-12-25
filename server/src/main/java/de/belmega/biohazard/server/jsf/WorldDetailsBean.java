package de.belmega.biohazard.server.jsf;

import de.belmega.biohazard.server.ejb.WorldDAO;
import de.belmega.biohazard.server.entities.WorldEntity;
import de.belmega.biohazard.server.persistence.ContinentState;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.util.Collection;

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
}

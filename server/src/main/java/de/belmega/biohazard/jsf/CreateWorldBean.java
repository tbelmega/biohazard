package de.belmega.biohazard.jsf;

import de.belmega.biohazard.ejb.WorldDAO;
import de.belmega.biohazard.server.entities.WorldEntity;

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
    private WorldEntity worldEntity;

    public String destroyWorldEntity() {
        worldEntity = null;
        return "index";
    }

    public WorldEntity getWorldEntity() {
        if (worldEntity == null)
            worldEntity = new WorldEntity();
        return worldEntity;
    }

    public void setWorldEntity(WorldEntity worldEntity) {
        this.worldEntity = worldEntity;
    }

    public String submit() {
        worldDAO.saveWorld(getWorldEntity());
        worldEntity = null;
        return "index";
    }
}

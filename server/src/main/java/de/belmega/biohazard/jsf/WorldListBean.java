package de.belmega.biohazard.jsf;

import de.belmega.biohazard.ejb.WorldDAO;
import de.belmega.biohazard.server.entities.WorldEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * @Author tbelmega on 03.12.2016.
 */
@ManagedBean
@RequestScoped
public class WorldListBean {
    @Inject
    WorldDAO worldDAO;
    private List<WorldEntity> worlds;

    public List<WorldEntity> getWorlds() {
        if (worlds == null) {
            worlds = worldDAO.listWorlds();
        }
        return worlds;
    }
}

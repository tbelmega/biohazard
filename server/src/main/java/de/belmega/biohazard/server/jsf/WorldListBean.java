package de.belmega.biohazard.server.jsf;

import de.belmega.biohazard.server.ejb.WorldDAO;
import de.belmega.biohazard.server.entities.WorldEntity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * @author tbelmega on 03.12.2016.
 */
@ManagedBean
@RequestScoped
public class WorldListBean {
    @Inject
    WorldDAO worldDAO;
    private List<WorldEntity> worlds;
    private String filter;
    private boolean orderAscending = true;

    public List<WorldEntity> getWorlds() {
        if (worlds == null) {
            worlds = worldDAO.listWorlds(filter, orderAscending);
        }
        return worlds;
    }

    public boolean isOrderAscending() {
        return orderAscending;
    }

    public void setOrderAscending(boolean orderAscending) {
        this.orderAscending = orderAscending;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
        this.worlds = null;
    }
}

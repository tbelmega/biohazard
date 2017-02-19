package de.belmega.biohazard.server.jsf;

import de.belmega.biohazard.server.persistence.dao.WorldDAO;
import de.belmega.biohazard.server.persistence.entities.WorldSimulationEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class WorldListBean {

    public static final String INDEX_PAGE = "index";
    public static final String INDEX_PAGE_REDIRECT = INDEX_PAGE + "?faces-redirect=true";

    @Inject
    WorldDAO worldDAO;
    private List<WorldSimulationEntity> worlds;
    private String filter;
    private boolean orderAscending = true;

    public List<WorldSimulationEntity> getWorlds() {
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

package de.belmega.biohazard.ejb;

import de.belmega.biohazard.server.entities.WorldEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author tbelmega on 03.12.2016.
 */
@Stateless
public class WorldDAO {
    @PersistenceContext
    EntityManager em;

    public List<WorldEntity> listWorlds() {
        return em.createQuery("SELECT w FROM WorldEntity w", WorldEntity.class).getResultList();
    }
}

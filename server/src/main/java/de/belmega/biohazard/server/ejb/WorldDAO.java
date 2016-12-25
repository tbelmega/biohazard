package de.belmega.biohazard.server.ejb;

import de.belmega.biohazard.server.entities.WorldEntity;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author tbelmega on 03.12.2016.
 */
@Stateless
public class WorldDAO {
    @PersistenceContext
    EntityManager em;

    public List<WorldEntity> listWorlds() {
        return listWorlds(null, true);
    }

    public List<WorldEntity> listWorlds(String filter, boolean asc) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<WorldEntity> query = criteriaBuilder.createQuery(WorldEntity.class);
        Root<WorldEntity> worldEntityRoot = query.from(WorldEntity.class);

        query.select(worldEntityRoot);

        if (asc)
            query.orderBy(criteriaBuilder.asc(worldEntityRoot.get("creationDate")));
        else
            query.orderBy(criteriaBuilder.desc(worldEntityRoot.get("creationDate")));

        if (!StringUtils.isEmpty(filter))
            query.where(criteriaBuilder.like(worldEntityRoot.get("name"), "%" + filter + "%"));

        return em.createQuery(query).getResultList();
    }

    public WorldEntity findWorld(long worldId) {
        return em.find(WorldEntity.class, worldId);
    }

    public void saveWorld(WorldEntity worldEntity) {
        em.persist(worldEntity);
    }

    public void destroyWorld(WorldEntity worldEntity) {
        WorldEntity toRemove;
        if (em.contains(worldEntity))
            toRemove = worldEntity;
        else
            toRemove = em.merge(worldEntity);
        em.remove(toRemove);
    }
}

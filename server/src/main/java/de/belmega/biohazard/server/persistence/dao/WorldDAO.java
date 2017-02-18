package de.belmega.biohazard.server.persistence.dao;

import de.belmega.biohazard.server.persistence.entities.WorldSimulationEntity;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@Stateless
public class WorldDAO {
    @PersistenceContext
    EntityManager em;

    public List<WorldSimulationEntity> listWorlds() {
        return listWorlds(null, true);
    }

    public List<WorldSimulationEntity> listWorlds(String filter, boolean asc) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<WorldSimulationEntity> query = criteriaBuilder.createQuery(WorldSimulationEntity.class);
        Root<WorldSimulationEntity> worldEntityRoot = query.from(WorldSimulationEntity.class);

        query.select(worldEntityRoot);

        if (asc)
            query.orderBy(criteriaBuilder.asc(worldEntityRoot.get("creationDate")));
        else
            query.orderBy(criteriaBuilder.desc(worldEntityRoot.get("creationDate")));

        if (!StringUtils.isEmpty(filter))
            query.where(criteriaBuilder.like(worldEntityRoot.get("name"), "%" + filter + "%"));

        return em.createQuery(query).getResultList();
    }

    public WorldSimulationEntity findWorld(long worldId) {
        return em.find(WorldSimulationEntity.class, worldId);
    }

    public void saveWorld(WorldSimulationEntity worldSimulationEntity) {
        WorldSimulationEntity worldSimulationEntity1 = findWorld(worldSimulationEntity.getId());
        if (worldSimulationEntity1 != null)
            em.merge(worldSimulationEntity);
        else
            em.persist(worldSimulationEntity);
    }

    public void destroyWorld(WorldSimulationEntity worldSimulationEntity) {
        WorldSimulationEntity toRemove;
        if (em.contains(worldSimulationEntity))
            toRemove = worldSimulationEntity;
        else
            toRemove = em.merge(worldSimulationEntity);
        em.remove(toRemove);
    }
}

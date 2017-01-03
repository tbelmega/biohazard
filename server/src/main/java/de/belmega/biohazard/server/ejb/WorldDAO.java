package de.belmega.biohazard.server.ejb;

import de.belmega.biohazard.server.entities.WorldEntity;
import de.belmega.biohazard.server.persistence.*;
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
        WorldEntity worldEntity1 = findWorld(worldEntity.getId());
        if (worldEntity1 != null)
            mergeWorldWithAllChildren(worldEntity);
        else
            persistWorldWithAllChildren(worldEntity);
    }

    private void mergeWorldWithAllChildren(WorldEntity worldEntity) {
        WorldState worldState = worldEntity.getWorldState();

        for (ContinentState c : worldState.getContinents())
            mergeContinentWithAllChildren(c);


        for (DiseaseState d : worldState.getDiseases())
            em.merge(d);

        WorldState mergedWorldState = em.merge(worldState);
        worldEntity.setWorldState(mergedWorldState);
        em.merge(worldEntity);
        em.flush();
    }

    private void mergeContinentWithAllChildren(ContinentState c) {
        for (CountryState country : c.getCountries())
            mergeCountryWithAllChildren(country);
        em.merge(c);
    }

    private void mergeCountryWithAllChildren(CountryState country) {
        for (InfectionState i : country.getInfectedPeoplePerDisease())
            em.merge(i);

        em.merge(country);
    }

    private void persistWorldWithAllChildren(WorldEntity worldEntity) {
        WorldState worldState = worldEntity.getWorldState();

        for (ContinentState c : worldState.getContinents())
            persistContinentWithAllChildren(c);

        for (DiseaseState d : worldState.getDiseases())
            em.persist(d);

        em.persist(worldState);
        em.persist(worldEntity);
        em.flush();
    }

    private void persistContinentWithAllChildren(ContinentState c) {
        for (CountryState country : c.getCountries())
            persistCountryWithAllChildren(country);

        em.persist(c);
    }

    private void persistCountryWithAllChildren(CountryState country) {
        for (InfectionState i : country.getInfectedPeoplePerDisease())
            em.persist(i);

        em.persist(country);
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

package de.belmega.biohazard.server.ejb;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.core.disease.Disease;
import de.belmega.biohazard.core.world.Continent;
import de.belmega.biohazard.core.world.World;
import de.belmega.biohazard.server.persistence.entities.WorldEntity;
import de.belmega.biohazard.server.persistence.state.WorldState;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author tbelmega on 04.12.2016.
 */
@Singleton
@Startup
public class TestDataGenerator {
    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void setupTestData() {
        Disease influenza = new Disease("Influenza", 0.3);
        Disease avianFlu = new Disease("Avian Flu", 0.05);

        Country germany = new Country("Germany", 80000000L);
        germany.setPopulationGrowthFactor(0.01);
        germany.add(avianFlu, 800L);
        Country poland = new Country("Poland", 40000000L);
        poland.setPopulationGrowthFactor(0.015);
        poland.add(avianFlu, 150L);
        Continent europe = new Continent("Europe");
        europe.add(germany);
        europe.add(poland);

        Country southPole = new Country("South Pole", 1000L);
        southPole.add(influenza, 500L);
        Continent antarctica = new Continent("Antarctica");
        antarctica.add(southPole);

        World earth = new World();
        earth.add(europe, antarctica);

        WorldEntity worldEntity = new WorldEntity("Test World One");
        worldEntity.setWorldState(WorldState.getState(earth));

        em.persist(worldEntity);
    }
}

package de.belmega.biohazard.server.ejb;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.core.world.Continent;
import de.belmega.biohazard.core.world.World;
import de.belmega.biohazard.server.entities.WorldEntity;

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
        Country germany = new Country("Germany", 80000000L);
        Continent europe = new Continent("Europe");
        europe.add(germany);
        World earth = new World();
        earth.add(europe);

        WorldEntity entity1 = new WorldEntity("Test World One");
        entity1.setWorldState(earth.getState());
        em.persist(entity1);
        em.persist(new WorldEntity("Test World Two"));
        em.persist(new WorldEntity("Test World Three"));
    }
}

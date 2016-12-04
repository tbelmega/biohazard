package de.belmega.biohazard.ejb;

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
        em.persist(new WorldEntity("Test World One"));
        em.persist(new WorldEntity("Test World Two"));
        em.persist(new WorldEntity("Test World Three"));
    }
}

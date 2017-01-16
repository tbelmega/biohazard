package de.belmega.biohazard.server.ejb;

import de.belmega.biohazard.server.persistence.entities.WorldSimulationEntity;
import de.belmega.biohazard.server.persistence.state.ContinentState;
import de.belmega.biohazard.server.persistence.state.CountryState;
import de.belmega.biohazard.server.persistence.state.DiseaseState;
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
        DiseaseState influenza = new DiseaseState("Influenza", 0.01);
        influenza.setLethalityFactor(0.01);
        DiseaseState avianFlu = new DiseaseState("Avian Flu", 0.03);
        avianFlu.setLethalityFactor(0.02);

        CountryState germanyState = new CountryState("Germany", 80000000L);
        germanyState.setGrowthFactor(0.01 / 365);
        germanyState.add(avianFlu, 10000L);
        CountryState polandState = new CountryState("Poland", 40000000L);
        polandState.setGrowthFactor(0.015 / 365);
        polandState.add(avianFlu, 150L);
        ContinentState europeState = new ContinentState("Europe");
        europeState.add(germanyState, polandState);

        CountryState southPole = new CountryState("South Pole", 1000L);
        southPole.add(influenza, 500L);
        ContinentState antarcticaState = new ContinentState("Antarctica");
        antarcticaState.add(southPole);

        WorldState earthState = new WorldState();
        earthState.add(antarcticaState, europeState);
        earthState.add(avianFlu, influenza);

        WorldSimulationEntity worldSimulationEntity = new WorldSimulationEntity("Test World One");
        worldSimulationEntity.setWorldState(earthState);

        em.persist(worldSimulationEntity);
    }
}

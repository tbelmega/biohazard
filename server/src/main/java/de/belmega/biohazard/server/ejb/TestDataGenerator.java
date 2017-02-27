package de.belmega.biohazard.server.ejb;

import de.belmega.biohazard.auth.common.dto.UserDTO;
import de.belmega.biohazard.auth.persistence.dao.UserDAO;
import de.belmega.biohazard.auth.persistence.entities.ApplicationRole;
import de.belmega.biohazard.core.country.TravelRoute;
import de.belmega.biohazard.core.country.TravelRouteType;
import de.belmega.biohazard.server.persistence.entities.WorldSimulationEntity;
import de.belmega.biohazard.server.persistence.state.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Insert test data into database when server starts up.
 * Remove for production environment.
 */
@Singleton
@Startup
public class TestDataGenerator {
    @PersistenceContext
    EntityManager em;

    @Inject
    UserDAO userDAO;

    @PostConstruct
    public void setupTestData() {
        setupTestUser();
        setupTestWorld();
    }

    private void setupTestUser() {
        UserDTO user = new UserDTO();
        user.setMailAddress("kenn@ich.net");
        user.setPassword("1234");
        user.addRole(ApplicationRole.ADMIN);

        userDAO.saveUser(user);
    }

    private void setupTestWorld() {
        DiseaseState influenza = new DiseaseState("Influenza", 0.01);
        influenza.setLethalityFactor(0.01);
        DiseaseState avianFlu = new DiseaseState("Avian Flu", 0.03);
        avianFlu.setLethalityFactor(0.02);
        DiseaseState plague = new DiseaseState("Plague", 0.05);
        plague.setLethalityFactor(0.5);

        CountryState germanyState = new CountryState("Germany", 80000000L);
        germanyState.setGrowthFactor(0.01 / 365);

        CountryState polandState = new CountryState("Poland", 40000000L);
        polandState.setGrowthFactor(0.015 / 365);

        ContinentState europeState = new ContinentState("Europe");
        europeState.add(germanyState, polandState);

        CountryState southPole = new CountryState("South Pole", 1000L);

        ContinentState antarcticaState = new ContinentState("Antarctica");
        antarcticaState.add(southPole);

        WorldState earthState = new WorldState();
        earthState.add(antarcticaState, europeState);
        earthState.add(avianFlu, influenza, plague);

        TravelRoute route = TravelRoute.create(germanyState, polandState, TravelRouteType.LAND);
        route.setTravelingPopulationFactorPerTick(0.001);

        WorldSimulationEntity worldSimulationEntity = new WorldSimulationEntity("Test World One");
        worldSimulationEntity.setWorldState(earthState);

        InfectionState.create(germanyState, avianFlu, 1000L);
        InfectionState.create(germanyState, plague, 10000L);
        InfectionState.create(polandState, avianFlu, 150L);
        InfectionState.create(southPole, influenza, 500L);

        em.persist(worldSimulationEntity);

    }
}

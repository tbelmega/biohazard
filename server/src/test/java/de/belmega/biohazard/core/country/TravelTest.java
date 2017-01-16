package de.belmega.biohazard.core.country;

import de.belmega.biohazard.core.world.World;
import de.belmega.biohazard.server.persistence.state.ContinentState;
import de.belmega.biohazard.server.persistence.state.CountryState;
import de.belmega.biohazard.server.persistence.state.DiseaseState;
import de.belmega.biohazard.server.persistence.state.WorldState;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

public class TravelTest {

    @Test
    public void testThat_travelRouteCalculatesTravelingPeoplePerTick() throws Exception {
        //arrange
        CountryState germanyState = new CountryState("Germany", 80000000L);
        CountryState polandState = new CountryState("Poland", 40000000L);
        TravelRoute route = TravelRoute.create(germanyState, polandState, TravelRouteType.LAND);
        route.setTravelingPopulationFactorPerTick(0.001);

        //act
        long travelersPerTick = route.getTravelersPerTick();

        //assert
        assertThat(travelersPerTick, is(equalTo(120000L)));
    }


    @Test
    public void testThat_travelersCarryDeseases() throws Exception {
        //arrange
        DiseaseState diseaseState = new DiseaseState("Avian Flu");

        CountryState germanyState = new CountryState("Germany", 80000000L);
        germanyState.add(diseaseState, 40000000L);

        CountryState polandState = new CountryState("Poland", 40000000L);
        TravelRoute route = TravelRoute.create(germanyState, polandState, TravelRouteType.LAND);
        route.setTravelingPopulationFactorPerTick(0.001);

        ContinentState europeState = new ContinentState("Europe");
        europeState.add(germanyState, polandState);

        WorldState earthState = new WorldState();
        earthState.add(diseaseState);
        earthState.add(europeState);

        World earth = new World(earthState);

        Country germany = germanyState.build(earth);

        //act
        germany.tick();

        //assert
        assertThat(polandState.getInfectedPeoplePerDisease(), is(not(empty())));
    }


}

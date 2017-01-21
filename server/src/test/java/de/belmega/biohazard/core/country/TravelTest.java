package de.belmega.biohazard.core.country;

import de.belmega.biohazard.core.world.World;
import de.belmega.biohazard.server.persistence.state.ContinentState;
import de.belmega.biohazard.server.persistence.state.CountryState;
import de.belmega.biohazard.server.persistence.state.DiseaseState;
import de.belmega.biohazard.server.persistence.state.WorldState;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

public class TravelTest {

    private CountryState germany;
    private CountryState poland;
    private ContinentState europe;
    private WorldState earthState;

    @BeforeMethod
    public void setUp() {
        germany = new CountryState("Germany", 80000000L);
        poland = new CountryState("Poland", 40000000L);

        europe = new ContinentState("Europe");
        europe.add(germany, poland);

        earthState = new WorldState();
        earthState.add(europe);
    }

    @Test
    public void testThat_routeIsAddedToCountries() throws Exception {
        //arrange

        //act
        TravelRoute route = TravelRoute.create(germany, poland, TravelRouteType.LAND);

        //assert
        assertThat(germany.getRoutes(), contains(route));
        assertThat(poland.getRoutes(), contains(route));
    }


    @Test
    public void testThat_travelRouteCalculatesTravelingPeoplePerTick() throws Exception {
        //arrange
        TravelRoute route = TravelRoute.create(germany, poland, TravelRouteType.LAND);
        route.setTravelingPopulationFactorPerTick(0.001);

        //act
        long travelersPerTick = route.getTravelersPerTick();

        //assert
        assertThat(travelersPerTick, is(equalTo(120000L)));
    }


    @Test
    public void testThat_travelersCarryDiseases() throws Exception {
        //arrange
        DiseaseState avianFlu = new DiseaseState("Avian Flu");
        germany.addInfected(avianFlu, 40000000L);
        earthState.add(avianFlu);

        TravelRoute route = TravelRoute.create(germany, poland, TravelRouteType.LAND);
        route.setTravelingPopulationFactorPerTick(0.001);

        World earth = earthState.build();

        //act
        earth.tick();

        //assert
        assertThat(poland.getInfections(), is(not(empty())));
    }


}

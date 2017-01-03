package de.belmega.biohazard.core.world;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.server.persistence.ContinentState;
import de.belmega.biohazard.server.persistence.WorldState;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author tbelmega on 17.12.2016.
 */
public class WorldStateTest {

    @Test
    public void testThat_worldStateIsExtracted() throws Exception {
        //arrange
        World world = new World();
        Continent bar = new Continent("bar");
        Continent foo = new Continent("foo");
        world.add(bar, foo);


        //act
        WorldState state = WorldState.getState(world);

        //assert
        assertThat(state.getContinents(), containsInAnyOrder(ContinentState.getState(foo), ContinentState.getState(bar)));
    }

    @Test
    public void testThat_worldIsRestoredFromState() throws Exception {
        //arrange
        WorldState worldState = new WorldState();
        Continent foo = new Continent("foo");
        foo.add(new Country("quz", 80000000L));
        worldState.addContinent(foo);


        //act
        World world = worldState.build();

        //assert
        assertThat(world.getContinents(), containsInAnyOrder(foo));
        Continent continent = world.getContinent("foo");
        Country country = continent.getCountry("quz");
        assertThat(country.getPopulation(), is(equalTo(80000000L)));
    }
}

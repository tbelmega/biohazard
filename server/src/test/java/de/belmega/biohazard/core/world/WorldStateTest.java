package de.belmega.biohazard.core.world;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.server.persistence.state.ContinentState;
import de.belmega.biohazard.server.persistence.state.CountryState;
import de.belmega.biohazard.server.persistence.state.WorldState;
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
        World world = new World(new WorldState());
        Continent bar = new Continent(new ContinentState("bar"));
        Continent foo = new Continent(new ContinentState("foo"));
        world.add(bar, foo);


        //act
        WorldState state = world.getState();

        //assert
        assertThat(state.getContinents(), containsInAnyOrder(foo.getState(), bar.getState()));
    }

    @Test
    public void testThat_worldIsRestoredFromState() throws Exception {
        //arrange
        WorldState worldState = new WorldState();
        ContinentState fooState = new ContinentState("foo");
        fooState.add(new CountryState("quz", 80000000L));
        worldState.addContinent(fooState);


        //act
        World world = worldState.build();

        //assert
        assertThat(world.getContinents(), containsInAnyOrder(fooState));
        Continent continent = world.getContinent("foo");
        Country country = continent.getCountry("quz");
        assertThat(country.getState().getPopulation(), is(equalTo(80000000L)));
    }
}

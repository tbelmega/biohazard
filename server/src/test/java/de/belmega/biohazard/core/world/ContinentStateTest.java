package de.belmega.biohazard.core.world;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.server.persistence.state.ContinentState;
import de.belmega.biohazard.server.persistence.state.CountryState;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.IsEqual.equalTo;

public class ContinentStateTest {

    @Test
    public void testThat_continentStateIsExtracted() throws Exception {
        //arrange
        Continent continent = new Continent(new ContinentState("baz"));
        Country foo = new Country(new CountryState("foo", 80000000L));
        Country bar = new Country(new CountryState("bar", 20000L));
        continent.add(foo, bar);

        //act
        ContinentState state = continent.getState();

        //assert
        assertThat(state.getCountries(), containsInAnyOrder(foo.getState(), bar.getState()));
    }

    @Test
    public void testThat_continentIsRestoredFromState() throws Exception {
        //arrange
        ContinentState continentState = new ContinentState();
        continentState.setName("quz");
        Country foo = new Country(new CountryState("foo", 80000000L));
        continentState.addCountry(foo);

        //act
        Continent continent = continentState.build(World.NO_WORLD);

        //assert
        assertThat(continent.getState().getName(), is(equalTo("quz")));
        assertThat(continent.getCountries(), contains(foo));
    }
}

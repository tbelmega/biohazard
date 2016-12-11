package de.belmega.biohazard.core.world;

import de.belmega.biohazard.core.country.Country;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class ContinentStateTest {

    @Test
    public void testThat_continentStateIsExtracted() throws Exception {
        //arrange
        Continent continent = new Continent();
        continent.add(new Country("foo", 80000000), new Country("bar", 20000));

        //act
        ContinentState state = continent.getState();

        //assert
        assertThat(state.getCountries(), containsInAnyOrder("foo", "bar"));
    }
}

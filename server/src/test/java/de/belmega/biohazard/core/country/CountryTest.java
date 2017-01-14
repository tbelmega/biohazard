package de.belmega.biohazard.core.country;

import de.belmega.biohazard.server.persistence.state.CountryState;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class CountryTest {

    /**
     * Test population growth per tick.
     * If growth factor for the country is not set manually, the standard value is 0.
     * The test uses 0.01 divided by 365, simulating a 1% per year growth, taking a tick as one day.
     */
    @Test
    public void testThat_populationGrowsAfterTick() throws Exception {
        //arrange
        int initialPopulation = 80000000;
        long expectedPopulation = 80002192;
        Country country = new Country(new CountryState("baz", initialPopulation));
        country.getState().setGrowthFactor(0.01 / 365);

        //act
        country.tick();

        //assert
        assertThat(country.getState().getPopulation(), is(equalTo(expectedPopulation)));
    }

    @Test
    public void testThat_whenNoGrowthFactorIsSet_populationStaysSame() throws Exception {
        //arrange
        long initialPopulation = 80000000;
        Country country = new Country(new CountryState("baz", initialPopulation));

        //act
        country.tick();

        //assert
        assertThat(country.getState().getPopulation(), is(equalTo(initialPopulation)));
    }


    @Test
    public void testThat_populationShrinksWithCustomGrowthFactor() throws Exception {
        //arrange
        long initialPopulation = 80000000;
        double growthFactor = -0.01 / 365;
        long expectedPopulation = 79997808;
        Country country = new Country(new CountryState("baz", initialPopulation));

        //act
        country.getState().setGrowthFactor(growthFactor);
        country.tick();

        //assert
        assertThat(country.getState().getPopulation(), is(equalTo(expectedPopulation)));
    }

}

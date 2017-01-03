package de.belmega.biohazard.core.country;

import de.belmega.biohazard.core.disease.Disease;
import de.belmega.biohazard.server.persistence.CountryState;
import de.belmega.biohazard.server.persistence.InfectionState;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
        Country country = new Country("baz", initialPopulation);
        country.setPopulationGrowthFactor(0.01 / 365);

        //act
        country.tick();

        //assert
        assertThat(country.getPopulation(), is(equalTo(expectedPopulation)));
    }

    @Test
    public void testThat_whenNoGrowthFactorIsSet_populationStaysSame() throws Exception {
        //arrange
        long initialPopulation = 80000000;
        Country country = new Country("baz", initialPopulation);

        //act
        country.tick();

        //assert
        assertThat(country.getPopulation(), is(equalTo(initialPopulation)));
    }


    @Test
    public void testThat_populationShrinksWithCustomGrowthFactor() throws Exception {
        //arrange
        long initialPopulation = 80000000;
        double growthFactor = -0.01 / 365;
        long expectedPopulation = 79997808;
        Country country = new Country("baz", initialPopulation);

        //act
        country.setPopulationGrowthFactor(growthFactor);
        country.tick();

        //assert
        assertThat(country.getPopulation(), is(equalTo(expectedPopulation)));
    }

    @Test
    public void testThat_countryStateIsExtracted() throws Exception {
        //arrange
        long initialPopulation = 80000000;
        double growthFactor = -0.01 / 365;
        Country country = new Country("baz", initialPopulation);
        country.setPopulationGrowthFactor(growthFactor);

        Disease disease = new Disease("foo", 1.0);
        long initiallyInfected = 1000L;
        country.add(disease, initiallyInfected);

        //act
        CountryState state = CountryState.getState(country);

        //assert
        assertThat(state.getPopulation(), is(equalTo(initialPopulation)));
        assertThat(state.getGrowthFactor(), is(equalTo(growthFactor)));
        assertThat(state.getInfectedPeoplePerDisease(), containsInAnyOrder(new InfectionState(state, "foo", 1000L)));
    }

    @Test
    public void testThat_countryStateIsRestored() throws Exception {
        //arrange
        CountryState countryState = new CountryState();
        long initialPopulation = 80000000L;
        countryState.setPopulation(initialPopulation);
        double growthFactor = 0.01;
        countryState.setGrowthFactor(growthFactor);
        long infectedPeople = 1000L;
        countryState.addInfected("foo", infectedPeople);

        //act
        Country country = countryState.build();

        //assert
        assertThat(country.getPopulation(), is(equalTo(initialPopulation)));
        assertThat(country.getGrowthFactor(), is(equalTo(growthFactor)));
        assertThat(country.getInfectedPeople(new Disease("foo", 1.0)), is(equalTo(infectedPeople)));
    }
}

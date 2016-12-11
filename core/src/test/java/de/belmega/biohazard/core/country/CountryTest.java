package de.belmega.biohazard.core.country;

import de.belmega.biohazard.core.disease.Disease;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testng.AssertJUnit.assertEquals;

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
        Country country = new Country(initialPopulation);
        country.setPopulationGrowthFactor(0.01 / 365);

        //act
        country.tick();

        //assert
        assertEquals(expectedPopulation, country.getPopulation());
    }

    @Test
    public void testThat_whenNoGrowthFactorIsSet_populationStaysSame() throws Exception {
        //arrange
        int initialPopulation = 80000000;
        Country country = new Country(initialPopulation);

        //act
        country.tick();

        //assert
        assertEquals(initialPopulation, country.getPopulation());
    }


    @Test
    public void testThat_populationShrinksWithCustomGrowthFactor() throws Exception {
        //arrange
        int initialPopulation = 80000000;
        double growthFactor = -0.01 / 365;
        long expectedPopulation = 79997808;
        Country country = new Country(initialPopulation);

        //act
        country.setPopulationGrowthFactor(growthFactor);
        country.tick();

        //assert
        assertEquals(expectedPopulation, country.getPopulation());
    }

    @Test
    public void testThat_CountryStateIsExtracted() throws Exception {
        //arrange
        long initialPopulation = 80000000;
        double growthFactor = -0.01 / 365;
        Country country = new Country(initialPopulation);
        country.setPopulationGrowthFactor(growthFactor);

        Disease disease = new Disease("foo", 1.0);
        long initiallyInfected = 1000L;
        country.add(disease, initiallyInfected);

        //act
        CountryState state = country.getState();

        //assert
        assertThat(state.getPopulation(), is(equalTo(initialPopulation)));
        assertThat(state.getGrowthFactor(), is(equalTo(growthFactor)));
        assertThat(state.getInfectedPeoplePerDisease().get("foo"), is(equalTo(initiallyInfected)));
    }

    @Test
    public void testThat_CountryStateIsRestored() throws Exception {
        //arrange
        CountryState countryState = new CountryState();
        long initialPopulation = 80000000L;
        countryState.setPopulation(initialPopulation);
        double growthFactor = 0.01;
        countryState.setGrowthFactor(growthFactor);
        long infectedPeople = 1000L;
        countryState.addInfected("foo", infectedPeople);

        //act
        Country country = Country.build(countryState);

        //assert
        assertThat(country.getPopulation(), is(equalTo(initialPopulation)));
        assertThat(country.getGrowthFactor(), is(equalTo(growthFactor)));
        assertThat(country.getInfectedPeople(new Disease("foo", 1.0)), is(equalTo(infectedPeople)));
    }
}

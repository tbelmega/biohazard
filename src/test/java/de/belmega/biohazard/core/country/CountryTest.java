package de.belmega.biohazard.core.country;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class CountryTest {

    /**
     * Test population growth per tick.
     * If growth factor for the country is not set manually, the standard value is 0.01 divided by 365,
     * simulating a 1% per year growth, taking a tick as one day.
     */
    @Test
    public void testThat_populationGrowsAfterTick() throws Exception {
        //arrange
        int initialPopulation = 80000000;
        long expectedPopulation = 80002192;
        Country country = new Country(initialPopulation);

        //act
        country.tick();

        //assert
        assertEquals(expectedPopulation, country.getPopulation());
    }

    @Test
    public void testThat_populationGrowsWithCustomGrowthFactor() throws Exception {
        //arrange
        int initialPopulation = 80000000;
        double growthFactor = 0.02 / 365;
        long expectedPopulation = 80004384;
        Country country = new Country(initialPopulation);

        //act
        country.setPopulationGrowthFactor(growthFactor);
        country.tick();

        //assert
        assertEquals(expectedPopulation, country.getPopulation());
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
}

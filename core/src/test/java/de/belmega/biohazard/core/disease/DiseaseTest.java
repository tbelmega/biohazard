package de.belmega.biohazard.core.disease;

import de.belmega.biohazard.core.country.Country;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class DiseaseTest {

    @Test
    public void testThat_diseaseSpreadsInCountry() throws Exception {
        //arrange
        Country country = new Country(80000000);
        Disease disease = new Disease("foo", 0.1);
        country.add(disease, 1000);

        //act
        country.tick();

        //assert
        assertEquals(1100, country.getInfectedPeople(disease));
    }

    @Test
    public void testThat_diseaseSpreadsInCountryUpToPopulationLimit() throws Exception {
        //arrange
        int initialPopulation = 80000000;
        Country country = new Country(initialPopulation);
        country.setPopulationGrowthFactor(0);
        Disease disease = new Disease("foo", 0.1);
        country.add(disease, 75000000);

        //act
        country.tick();

        //assert
        assertEquals(initialPopulation, country.getInfectedPeople(disease));
    }

    /**
     * With low numbers of infected people, the spread per tick may be less than 1 person, rounded down to zero.
     * In this case, the plain calculation should be replaced by a proper percent chance to infect one person.
     * The chance is checked once per tick.
     */
    @Test(timeOut = 100)
    public void testThat_diseaseSpreadsInCountryByChance() throws Exception {
        //arrange
        Country country = new Country(80000000);
        Disease disease = new Disease("foo", 0.1);
        long amountOfInfectedPeople = 4;
        country.add(disease, amountOfInfectedPeople);

        //act
        while (country.getInfectedPeople(disease) == amountOfInfectedPeople) {
            // With the given values, the infection chance is 40% per tick.
            country.tick();
        }

        //assert
        assertEquals(5, country.getInfectedPeople(disease));
    }
}

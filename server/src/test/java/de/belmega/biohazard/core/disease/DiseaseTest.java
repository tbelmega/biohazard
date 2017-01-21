package de.belmega.biohazard.core.disease;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.server.persistence.state.CountryState;
import de.belmega.biohazard.server.persistence.state.DiseaseState;
import de.belmega.biohazard.server.persistence.state.InfectionState;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class DiseaseTest {

    private DiseaseState disease;
    private CountryState countryState;

    @BeforeMethod
    public void setUp() {
        countryState = new CountryState("baz", 80000000);
        disease = new DiseaseState("foo", 0.1);
    }

    @Test
    public void testThat_diseaseSpreadsInCountry() throws Exception {
        //arrange
        InfectionState.create(countryState, disease, 1000);
        Country country = new Country(this.countryState);

        //act
        country.tick();

        //assert
        assertEquals(1100, country.getInfectedPeople(disease));
    }

    @Test
    public void testThat_diseaseSpreadsInCountryUpToPopulationLimit() throws Exception {
        //arrange
        int initialPopulation = 80000000;
        CountryState baz = new CountryState("baz", initialPopulation);
        Country country = new Country(baz);

        baz.setGrowthFactor(0);

        InfectionState.create(countryState, disease, 75000000);

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
        Country country = new Country(countryState);
        long amountOfInfectedPeople = 4;

        InfectionState.create(countryState, disease, 4);

        //act
        while (country.getInfectedPeople(disease) == amountOfInfectedPeople) {
            // With the given values, the infection chance is 40% per tick.
            country.tick();
        }

        //assert
        assertEquals(5, country.getInfectedPeople(disease));
    }
}

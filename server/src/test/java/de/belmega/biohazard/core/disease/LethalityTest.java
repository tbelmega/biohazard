package de.belmega.biohazard.core.disease;

import de.belmega.biohazard.core.country.Country;
import de.belmega.biohazard.server.persistence.state.CountryState;
import de.belmega.biohazard.server.persistence.state.DiseaseState;
import de.belmega.biohazard.server.persistence.state.InfectionState;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by tbelmega on 03.12.2016.
 */
public class LethalityTest {

    private DiseaseState disease;
    private CountryState countryState;

    @BeforeMethod
    public void setUp() {
        disease = new DiseaseState("foo", 0);
        disease.setLethalityFactor(0.1);

        countryState = new CountryState("baz", 1000);
    }

    @Test
    public void testThat_lethalDiseaseKillsPeople() throws Exception {
        //arrange
        InfectionState.create(countryState, disease, 100);

        Country country = new Country(countryState);

        //act
        country.tick();

        //assert
        long expectedDead = Math.round(100 * 0.1);
        assertThat(country.getState().getDeceasedPopulation(), is(equalTo(expectedDead)));

        long expectedPopulation = 1000 - expectedDead;
        assertThat(country.getState().getPopulation(), is(equalTo(expectedPopulation)));
    }

    @Test
    public void testThat_lethalDiseaseKillsInfectedPeople() throws Exception {
        //arrange
        InfectionState.create(countryState, disease, 100);

        Country country = new Country(countryState);

        //act
        country.tick();

        //assert
        long expectedDead = Math.round(100 * 0.1);
        long expectedInfectedPopulation = 100 - expectedDead;
        assertEquals(expectedInfectedPopulation, country.getInfectedPeople(disease));
    }

    /**
     * With low numbers of infected people, the kill rate per tick may be less than 1 person, rounded down to zero.
     * In this case, the plain calculation should be replaced by a proper percent chance to kill one person.
     * The chance is checked once per tick.
     */
    @Test(timeOut = 100)
    public void testThat_lethalDiseaseKillsInfectedPeopleByChance() throws Exception {
        //arrange
        long amountOfInfectedPeople = 4;
        InfectionState.create(countryState, disease, amountOfInfectedPeople);

        Country country = new Country(countryState);

        //act
        while (country.getInfectedPeople(disease) == amountOfInfectedPeople) {
            // With the given values, the kill chance is 40% per tick.
            country.tick();
        }

        //assert
        assertEquals(3, country.getInfectedPeople(disease));
    }

    @Test
    public void testThat_lethalityFactorIsAtMostOne() throws Exception {
        //arrange

        //act
        disease.setLethalityFactor(2);

        //assert
        assertEquals(DiseaseState.MAX_LETHALITY_FACTOR, disease.getLethalityFactor());
    }


    @Test
    public void testThat_lethalityFactorIsAtLeastZero() throws Exception {
        //arrange

        //act
        disease.setLethalityFactor(-1);

        //assert
        assertEquals(DiseaseState.MIN_LETHALITY_FACTOR, disease.getLethalityFactor());
    }


    /**
     * If 500 people out of 10000 die from disease1 and 500 die from disease2,
     * there are around 25 people dying from both diseases at the same time.
     * Therefore the total kill count is not 500 + 500, but 500 + 500 - 25.
     */
    @Test
    public void testThat_twoLethalDiseasesOverlapInKilling() throws Exception {
        //arrange
        int initialPopulation = 10000;
        Country country = new Country(new CountryState("baz", initialPopulation));


        InfectionState.create(countryState, disease, 5000);

        DiseaseState disease2 = new DiseaseState("bar", 0);
        disease2.setLethalityFactor(0.1);

        InfectionState.create(countryState, disease2, 5000);

        //act
        country.tick();

        //assert
        long expectedDead = 975; // 500 + 500 - 25
        long expectedPopulation = initialPopulation - expectedDead;
        assertEquals(expectedPopulation, country.getState().getPopulation());
    }
}

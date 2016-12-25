package de.belmega.biohazard.core.disease;

import de.belmega.biohazard.core.country.Country;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by tbelmega on 03.12.2016.
 */
public class LethalityTest {

    @Test
    public void testThat_lethalDiseaseKillsPeople() throws Exception {
        //arrange
        Country country = new Country("baz", 1000);
        Disease disease = new Disease("foo", 0);
        disease.setLethalityFactor(0.1);
        country.add(disease, 100);

        //act
        country.tick();

        //assert
        long expectedDead = Math.round(100 * 0.1);
        long expectedPopulation = 1000 - expectedDead;
        assertEquals(expectedPopulation, country.getPopulation());
    }

    @Test
    public void testThat_lethalDiseaseKillsInfectedPeople() throws Exception {
        //arrange
        Country country = new Country("baz", 1000);
        Disease disease = new Disease("foo", 0);
        disease.setLethalityFactor(0.1);
        country.add(disease, 100);

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
        Country country = new Country("baz", 1000);
        Disease disease = new Disease("foo", 0);
        disease.setLethalityFactor(0.1);
        long amountOfInfectedPeople = 4;
        country.add(disease, amountOfInfectedPeople);

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
        Disease disease = new Disease("foo", 0);

        //act
        disease.setLethalityFactor(2);

        //assert
        assertEquals(Disease.MAX_LETHALITY_FACTOR, disease.getLethalityFactor());
    }


    @Test
    public void testThat_lethalityFactorIsAtLeastZero() throws Exception {
        //arrange
        Disease disease = new Disease("foo", 0);

        //act
        disease.setLethalityFactor(-1);

        //assert
        assertEquals(Disease.MIN_LETHALITY_FACTOR, disease.getLethalityFactor());
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
        Country country = new Country("baz", initialPopulation);
        Disease disease1 = new Disease("foo", 0);
        disease1.setLethalityFactor(0.1);
        country.add(disease1, 5000);

        Disease disease2 = new Disease("bar", 0);
        disease2.setLethalityFactor(0.1);
        country.add(disease2, 5000);

        //act
        country.tick();

        //assert
        long expectedDead = 975; // 500 + 500 - 25
        long expectedPopulation = initialPopulation - expectedDead;
        assertEquals(expectedPopulation, country.getPopulation());
    }
}

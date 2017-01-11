package de.belmega.biohazard.core.world;

import de.belmega.biohazard.core.country.Country;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WorldGenerationTest {

    @Test
    public void testThat_numberOfContinents_isInGivenRange() throws Exception {
        //arrange
        WorldGenerationParams params = new WorldGenerationParams();
        params.setMinContinents(2);
        params.setMaxContinents(4);

        //act
        World newWorld = new WorldGenerator(params).generate();

        //assert
        assertThat(newWorld.getContinents().size(), isOneOf(2, 3, 4));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testThat_whenRangeIsNegative_throwsException() throws Exception {
        //arrange
        WorldGenerationParams params = new WorldGenerationParams();
        params.setMinContinents(5);
        params.setMaxContinents(3);

        //act
        new WorldGenerator(params).generate();
    }

    @Test
    public void testThat_standardRangeOfContinents_is5To7() throws Exception {
        //arrange
        WorldGenerationParams params = new WorldGenerationParams();

        //act
        World newWorld = new WorldGenerator(params).generate();

        //assert
        assertThat(newWorld.getContinents().size(), isOneOf(5, 6, 7));
    }

    @Test
    public void testThat_standardRangeOfCountries_is25To70() throws Exception {
        //arrange
        WorldGenerationParams params = new WorldGenerationParams();

        //act
        World newWorld = new WorldGenerator(params).generate();

        //assert
        assertThat(newWorld.getCountries().size(),
                is(allOf(greaterThanOrEqualTo(25), lessThanOrEqualTo(70))));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testThat_whenCountryRangeIsNegative_throwsException() throws Exception {
        //arrange
        WorldGenerationParams params = new WorldGenerationParams();
        params.setMinCountriesPerContinent(5);
        params.setMaxCountriesPerContinent(3);

        //act
        new WorldGenerator(params).generate();
    }

    @Test
    public void testThat_numberOfCountries_isInGivenRange() throws Exception {
        //arrange
        WorldGenerationParams params = new WorldGenerationParams();
        params.setMinCountriesPerContinent(2);
        params.setMaxCountriesPerContinent(2);

        //act
        World newWorld = new WorldGenerator(params).generate();

        //assert
        assertThat(newWorld.getCountries().size(),
                is(allOf(greaterThanOrEqualTo(10), lessThanOrEqualTo(14))));
    }

    @Test
    public void testThat_standardRangeOfPopulation_is90000_to1600000000() throws Exception {
        //arrange
        WorldGenerationParams params = new WorldGenerationParams();

        //act
        Country country = new WorldGenerator(params).generateCountry();

        //assert
        assertThat(country.getState().getPopulation(),
                is(allOf(greaterThanOrEqualTo(90000L), lessThanOrEqualTo(1600000000L))));
    }
}

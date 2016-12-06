package de.belmega.biohazard.core.world;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isOneOf;

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
}

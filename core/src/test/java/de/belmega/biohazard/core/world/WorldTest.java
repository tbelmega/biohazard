package de.belmega.biohazard.core.world;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

public class WorldTest {

    @Test(timeOut = 3000)
    public void testThat_whenWorldRuns_allContinentsTick() throws Exception {
        //arrange
        Continent continent1 = Mockito.mock(Continent.class);
        Continent continent2 = Mockito.mock(Continent.class);
        Continent continent3 = Mockito.mock(Continent.class);
        World world = new World();
        world.add(continent1, continent2, continent3);

        //act
        world.run(100);
        Thread.sleep(250);
        world.stop();

        //assert
        int expectedTicks = 250 / 100;
        verify(continent1, (atLeast(expectedTicks))).tick();
        verify(continent1, (atMost(expectedTicks + 1))).tick();
        verify(continent2, (atLeast(expectedTicks))).tick();
        verify(continent3, (atLeast(expectedTicks))).tick();
    }
}

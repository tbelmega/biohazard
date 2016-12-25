package de.belmega.biohazard.core.world;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

public class WorldRunnerTest {

    @Test(timeOut = 1000)
    public void testThat_whenWorldRunnerRuns_worldTicks() throws Exception {
        //arrange
        World world = Mockito.mock(World.class);
        WorldRunner runner = new WorldRunner(world, 10);

        //act
        Thread thread = new Thread(runner);
        thread.start();
        Thread.sleep(35);
        runner.stop();

        //assert
        verify(world, (atLeast(3))).tick();
        verify(world, (atMost(4))).tick();
    }
}

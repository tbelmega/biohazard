package de.belmega.biohazard.core.world;

import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
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

    @Test
    public void testThat_whenWorldRunnerRuns_ageIsIncreased() throws Exception {
        //arrange
        World world = new World();
        WorldRunner runner = new WorldRunner(world, 100);

        //act
        Thread thread = new Thread(runner);
        thread.start();
        Thread.sleep(350);
        runner.stop();

        //assert
        long actualWorldAge = world.getAge();
        assertThat(actualWorldAge, is(both(greaterThanOrEqualTo(3L)).and(lessThanOrEqualTo(4L))));
    }
}

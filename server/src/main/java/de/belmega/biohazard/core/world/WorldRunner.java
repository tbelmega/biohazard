package de.belmega.biohazard.core.world;

public class WorldRunner implements Runnable {
    private final World world;
    private final long millisecondsPerTick;
    private boolean running = false;

    public WorldRunner(World world, long millisecondsPerTick) {
        this.world = world;
        this.millisecondsPerTick = millisecondsPerTick;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            long startTick = System.currentTimeMillis();

            world.tick();

            long readyTick = System.currentTimeMillis();
            long elapsed = readyTick - startTick;
            long timeToWait = millisecondsPerTick - elapsed;
            if (timeToWait > 0) {
                try {
                    Thread.sleep(timeToWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stop() {
        running = false;
    }
}

package de.belmega.biohazard.core.world;

public class WorldGenerationParams {
    private int minContinents = 5;
    private int maxContinents = 7;

    public int getMaxContinents() {
        return maxContinents;
    }

    public void setMaxContinents(int maxContinents) {
        this.maxContinents = maxContinents;
    }

    public int getMinContinents() {
        return minContinents;
    }

    public void setMinContinents(int minContinents) {
        this.minContinents = minContinents;
    }
}

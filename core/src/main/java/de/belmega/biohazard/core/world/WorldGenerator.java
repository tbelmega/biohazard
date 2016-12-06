package de.belmega.biohazard.core.world;

public class WorldGenerator {

    private final WorldGenerationParams params;
    private World world;

    public WorldGenerator(WorldGenerationParams params) {
        this.params = params;
    }

    public World generate() {
        this.world = new World();
        world.add(generateContinents());
        return world;
    }

    private Continent[] generateContinents() {
        int range = params.getMaxContinents() - params.getMinContinents();

        if (range < 0) throw new IllegalArgumentException(
                "Maximal number of continents must be higher than minimal number of continents.");

        int numberOfContinents = params.getMinContinents() + (int) (Math.random() * range);
        Continent[] continents = new Continent[numberOfContinents];

        for (int i = 0; i < numberOfContinents; i++) {
            continents[i] = new Continent();
        }
        return continents;
    }
}

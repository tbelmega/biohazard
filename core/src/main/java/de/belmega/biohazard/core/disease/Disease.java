package de.belmega.biohazard.core.disease;

public class Disease {
    public static final double MAX_LETHALITY_FACTOR = 1.0;
    public static final double MIN_LETHALITY_FACTOR = 0.0;
    private final double spreadRate;
    private double lethalityFactor;

    public Disease(double spreadRate) {
        this.spreadRate = spreadRate;
    }

    public double getSpreadRate() {
        return spreadRate;
    }

    public double getLethalityFactor() {
        return lethalityFactor;
    }

    public void setLethalityFactor(double lethalityFactor) {
        if (MIN_LETHALITY_FACTOR < lethalityFactor && lethalityFactor < MAX_LETHALITY_FACTOR)
            this.lethalityFactor = lethalityFactor;
        else if (lethalityFactor >= MAX_LETHALITY_FACTOR)
            this.lethalityFactor = MAX_LETHALITY_FACTOR;
        else this.lethalityFactor = MIN_LETHALITY_FACTOR;
    }
}

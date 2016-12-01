package de.belmega.biohazard.core.disease;

public class Disease {
    private final double spreadRate;

    public Disease(double spreadRate) {
        this.spreadRate = spreadRate;
    }

    public double getSpreadRate() {
        return spreadRate;
    }
}

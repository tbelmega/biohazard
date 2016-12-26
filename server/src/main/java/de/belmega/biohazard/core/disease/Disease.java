package de.belmega.biohazard.core.disease;

import de.belmega.biohazard.server.persistence.DiseaseState;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Disease {
    public static final double MAX_LETHALITY_FACTOR = 1.0;
    public static final double MIN_LETHALITY_FACTOR = 0.0;
    private final double spreadRate;
    private final String name;
    private double lethalityFactor;

    public Disease(String name, double spreadRate) {
        this.spreadRate = spreadRate;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof Disease)) return false;
        Disease other = (Disease) o;

        return new EqualsBuilder()
                .append(this.name, other.getName())
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .hashCode();
    }

    public DiseaseState getState() {
        return new DiseaseState(this.name);
    }
}

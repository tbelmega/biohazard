package de.belmega.biohazard.server.persistence.state;

import de.belmega.biohazard.core.disease.Disease;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DiseaseState {

    @Id
    private String name;

    @ManyToOne
    @JoinColumn(name = "world", nullable = false)
    private WorldState world;
    private double spreadRate;

    public DiseaseState() {
    }

    public DiseaseState(String name) {
        this.name = name;
    }

    public static Disease build(DiseaseState d) {
        Disease disease = new Disease(d.getName(), d.getSpreadRate());
        return disease;
    }

    public static DiseaseState getState(Disease d) {
        return new DiseaseState(d.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WorldState getWorld() {
        return world;
    }

    public void setWorld(WorldState world) {
        this.world = world;
    }

    public double getSpreadRate() {
        return spreadRate;
    }

    public void setSpreadRate(double spreadRate) {
        this.spreadRate = spreadRate;
    }
}

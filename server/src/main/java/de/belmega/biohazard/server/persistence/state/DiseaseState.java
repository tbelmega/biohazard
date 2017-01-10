package de.belmega.biohazard.server.persistence.state;

import de.belmega.biohazard.core.disease.Disease;

import javax.persistence.*;

@Entity
public class DiseaseState extends NamedGameEntityState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "world", nullable = false)
    private WorldState world;
    private double spreadRate;
    private double lethalityFactor;

    public DiseaseState() {
    }

    public DiseaseState(String name) {
        this.setName(name);
    }

    public static Disease build(DiseaseState d) {
        Disease disease = new Disease(d.getName(), d.getSpreadRate());
        disease.setLethalityFactor(d.getLethalityFactor());
        return disease;
    }

    public static DiseaseState getState(Disease d) {
        DiseaseState diseaseState = new DiseaseState(d.getName());
        diseaseState.setSpreadRate(d.getSpreadRate());
        diseaseState.setLethalityFactor(d.getLethalityFactor());
        return diseaseState;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public double getLethalityFactor() {
        return lethalityFactor;
    }

    public void setLethalityFactor(double lethalityFactor) {
        this.lethalityFactor = lethalityFactor;
    }
}

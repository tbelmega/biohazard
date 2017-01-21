package de.belmega.biohazard.server.persistence.state;

import javax.persistence.*;

@Entity
public class DiseaseState extends NamedGameEntityState {

    public static final double MAX_LETHALITY_FACTOR = 1.0;
    public static final double MIN_LETHALITY_FACTOR = 0.0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "world", nullable = false)
    private WorldState world;


    private String name;
    private double spreadRate;
    private double lethalityFactor;

    public DiseaseState() {
    }

    public DiseaseState(String name) {
        this.setName(name);
    }

    public DiseaseState(String name, double spreadRate) {
        this.name = name;
        this.spreadRate = spreadRate;
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
        if (MIN_LETHALITY_FACTOR < lethalityFactor && lethalityFactor < MAX_LETHALITY_FACTOR)
            this.lethalityFactor = lethalityFactor;
        else if (lethalityFactor >= MAX_LETHALITY_FACTOR)
            this.lethalityFactor = MAX_LETHALITY_FACTOR;
        else this.lethalityFactor = MIN_LETHALITY_FACTOR;
    }
}

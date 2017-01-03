package de.belmega.biohazard.server.persistence;

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

    public DiseaseState() {
    }

    public DiseaseState(String name) {
        this.name = name;
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

}

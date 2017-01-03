package de.belmega.biohazard.server.persistence.entities;

import de.belmega.biohazard.server.persistence.state.WorldState;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author tbelmega on 03.12.2016.
 */
@Entity
public class WorldEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String name;

    private Timestamp creationDate;

    @OneToOne(cascade = CascadeType.ALL)
    private WorldState state;

    public WorldEntity() {
        this.creationDate = new Timestamp(System.currentTimeMillis());
    }

    public WorldEntity(String name) {
        this.name = name;
        this.creationDate = new Timestamp(System.currentTimeMillis());
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

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public WorldState getWorldState() {
        return state;
    }

    public void setWorldState(WorldState state) {
        this.state = state;
    }
}

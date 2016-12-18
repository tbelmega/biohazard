package de.belmega.biohazard.server.entities;

import de.belmega.biohazard.persistence.WorldState;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
}

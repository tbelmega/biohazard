package de.belmega.biohazard.server.entities;

import javax.persistence.*;

/**
 * @author tbelmega on 03.12.2016.
 */
@Entity
@Table(name = "WorldEntity", schema = "PUBLIC")
public class WorldEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String name;

    public WorldEntity() {
    }

    public WorldEntity(String name) {
        this.name = name;
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
}

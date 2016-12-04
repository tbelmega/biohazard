package de.belmega.biohazard.server.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

/**
 * @author tbelmega on 03.12.2016.
 */
@Entity
public class WorldEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String name;

    private Date creationDate;

    public WorldEntity() {
    }

    public WorldEntity(String name) {
        this.name = name;
        this.creationDate = new Date(System.currentTimeMillis());
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}

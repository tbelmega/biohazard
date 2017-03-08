package de.belmega.biohazard.server.dto;

import de.belmega.biohazard.server.persistence.entities.WorldSimulationEntity;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

@XmlRootElement
public class WorldDTO {
    private long id;
    private String name;
    private Timestamp creationTimestamp;

    public WorldDTO(WorldSimulationEntity worldSimulationEntity) {
        this.name = worldSimulationEntity.getName();
        this.creationTimestamp = worldSimulationEntity.getCreationDate();
        this.id = worldSimulationEntity.getId();
    }

    public WorldDTO() {
    }

    public String getName() {
        return name;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreationTimestamp(Timestamp creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

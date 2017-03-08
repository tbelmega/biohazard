package de.belmega.biohazard.server.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class WorldListDTO {
    private List<WorldDTO> worldList;

    public WorldListDTO(List<WorldDTO> worldDTOs) {
        this.worldList = worldDTOs;
    }

    public WorldListDTO() {
    }

    @XmlElement(name = "worlds")
    public List<WorldDTO> getWorldList() {
        return worldList;
    }
}

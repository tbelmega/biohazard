package de.belmega.biohazard.server.service;

import de.belmega.biohazard.server.dto.WorldDTO;
import de.belmega.biohazard.server.dto.WorldListDTO;
import de.belmega.biohazard.server.persistence.dao.WorldDAO;
import de.belmega.biohazard.server.persistence.entities.WorldSimulationEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class WorldService {

    @Inject
    WorldDAO worldDAO;


    public List<WorldDTO> getWorlds() {
        List<WorldSimulationEntity> worldEntityList = worldDAO.listWorlds("", true);
        List<WorldDTO> worldDTOs =
                worldEntityList
                        .stream()
                        .map(w -> createDTO(w))
                        .collect(Collectors.toList());
        return worldDTOs;
    }

    private WorldDTO createDTO(WorldSimulationEntity worldSimulationEntity) {
        WorldDTO dto = new WorldDTO(worldSimulationEntity);
        return dto;
    }

    public WorldDTO getWorldById(String id) {
        long worldId = Long.parseLong(id);
        WorldSimulationEntity world = worldDAO.findWorld(worldId);
        return new WorldDTO(world);
    }
}

package de.belmega.biohazard.rs;

import de.belmega.biohazard.server.dto.WorldDTO;
import de.belmega.biohazard.server.service.WorldService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("worlds")
public class WorldResource {

    @Inject
    WorldService worldService;

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<WorldDTO> getAll(){
        List<WorldDTO> worlds = worldService.getWorlds();
        return worlds;
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public WorldDTO getWorldByID(@PathParam("id") String id) {
        WorldDTO worldById = worldService.getWorldById(id);
        return worldById;
    }

}

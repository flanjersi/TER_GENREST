package fr.amu.terGENREST.controllers;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.amu.terGENREST.controllers.utils.Utils;
import fr.amu.terGENREST.entities.projectSpecifications.Corridor;
import fr.amu.terGENREST.services.projectSpecifications.CorridorManager;

@Path("api/corridors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CorridorManagerControllerREST {
	
	@EJB
	private CorridorManager corridorManager;
	
	public CorridorManagerControllerREST() {
		
	}
	
	@GET
	public Response getAllCorridors() {
		List<Corridor> corridors = corridorManager.findAllCorridor();
		return Response.ok().entity(corridors).build();
	}
	
	
	@GET
	@Path("/{id:[0-9]+}")
	public Response getCorridorById(@PathParam("id") Long id) {	
		Corridor corridor = corridorManager.findCorridor(id);
		if(corridor == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "No Corridor to id : " + id))
					.build();
		}
		return Response.ok().entity(corridor).build();
	}	
	
	@POST
	@Path("/{id:[0-9]+}")
	public Response updateCorridor(@PathParam("id") Long id, Corridor corridor) {
	    
		if( corridor.getNumberCorridor() == 0 ) {
			return Response
					.status(403)
					.entity(Utils.makeErrorMessage(404, " 'NumberCorridor' property is missing"))
					.build();
		}
		
		//TODO Changement vers la modification plus propre -> modification si != null
			
//		if(id != corridor.getId()) {
//			corridor.setId(id);
//		}
		corridorManager.updateCorridor(corridor);
		return Response.ok().entity(corridorManager.findCorridor(id)).build();
	}
	
	//TODO Création d'une méthode d'ajout de sensor (note à moi même)
	//TODO Création d'une méthode d'ajout de actuator
	//TODO Création d'une méthode de suppression de sensor (note à moi même)
	//TODO Création d'une méthode de suppression de actuator
	
}

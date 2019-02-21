package fr.amu.terGENREST.controllers;

import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.amu.terGENREST.controllers.utils.Utils;
import fr.amu.terGENREST.entities.projectSpecifications.Actuator;
import fr.amu.terGENREST.entities.projectSpecifications.Corridor;
import fr.amu.terGENREST.entities.user.User;
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
	    Corridor findedCorridor = corridorManager.findCorridor(id);
	    
		if( corridor.getNumberCorridor() != 0 ) {
			findedCorridor.setNumberCorridor(corridor.getNumberCorridor());
		}
					

		corridorManager.updateCorridor(findedCorridor);
		return Response.ok().entity(corridorManager.findCorridor(id)).build();
	}
	
	@PUT
	@Path("/{idCorridor:[0-9]+}/actuators/")
	public Response createActuator(@PathParam("idCorridor") Long idCorridor, Actuator actuator) {
		
		Corridor corridor = corridorManager.findCorridor(idCorridor);
		
		if(corridor == null){
			return  Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Corridor with id '" + idCorridor + "' not exist"))
					.build();
		}
					
		if(actuator.getBrand() == null) {
			return  Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'Brand' property is missing"))
					.build();
		}
		
		if(actuator.getModel() == null) {
			return  Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'Model' property is missing"))
					.build();
		}
		
		if(actuator.getReference() == null) {
			return  Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'Reference' property is missing"))
					.build();
		}
		if(actuator.getState() == null) {
			return  Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'State' property is missing"))
					.build();
		}
		if(actuator.getLongitude() == 0) {
			return  Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'Longitude' property is missing"))
					.build();
		}
		if(actuator.getLatitude() == 0) {
			return  Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'Latitude' property is missing"))
					.build();
		}

		corridor.addActuator(actuator);
		corridorManager.updateCorridor(corridor);
		
		corridor = corridorManager.findCorridor(corridor.getId());
		Optional<Actuator> addedActuator =  corridor.getActuators().stream().max((b1, b2) -> Long.compare(b1.getId(), b2.getId()));

		if (addedActuator.isPresent()) {
			JsonObject jsonResponse = Json.createObjectBuilder().add("id", addedActuator.get().getId()).build();
			
			return Response.status(201).entity(jsonResponse).build();		
		}
		else {
			return  Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Added actuator not found"))
					.build();
		}
		
	}
	
	@DELETE
	@Path("{idCorridor:[0-9]+}/actuators/{idActuator:[0-9]+}")
	public Response deleteActuator( @PathParam("idCorridor") Long idCorridor, 
										@PathParam("idActuator") Long idActuator) {
			Corridor corridor = corridorManager.findCorridor(idCorridor);
			
			if(corridor == null){
				return  Response
						.status(404)
						.entity(Utils.makeErrorMessage(404, "Corridor with id '" + idCorridor + "' not exist"))
						.build();
			}
			Optional<Actuator> actuator =  corridor.getActuators().stream().filter(a -> a.getId() == idActuator).findFirst();

			if(!actuator.isPresent()) {
				return Response
						.status(404)
						.entity(Utils.makeErrorMessage(404, "Actuator with id '" + idActuator + "' not found"))
						.build();
			}
			
			corridor.removeActuator(actuator.get());
			corridorManager.updateCorridor(corridor);

			return Response.ok().build();
	}
	
	@GET
	@Path("/{idCorridor:[0-9]+}/projects")
	public Response getAllActuators(@PathParam("idCorridor") Long idCorridor) {
		Corridor corridor = corridorManager.findCorridor(idCorridor);
		
		if(corridor == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "corridor with id '" + idCorridor + "' not found"))
					.build();
		}
		
		return Response.ok().entity(corridor.getActuators()).build();
	}
	
	
	//TODO Cr�ation d'une m�thode d'ajout de sensor (note � moi m�me)
	//TODO Cr�ation d'une m�thode de suppression de sensor (note � moi m�me)	
}

package fr.amu.terGENREST.controllers;

import java.util.List;

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
import fr.amu.terGENREST.entities.environmentTechnical.OperatingSystem;
import fr.amu.terGENREST.services.environmentTechnical.OperatingSystemManager;

@Path("api/operatingSystem")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OperatingSystemsManagerControllerRest {

	
	@EJB
	private OperatingSystemManager operatingSystemManager;
	
	public OperatingSystemsManagerControllerRest() {}
	
	@GET
	@Path("")
	public Response getAllOperatingSystem() {
		List<OperatingSystem> operatingSystems = operatingSystemManager.findAllOperatingSystem();
		
		return Response.ok().entity(operatingSystems).build();
	}
	
	@GET
	@Path("{id:[0-9]+}")
	public Response getOperatingSystemById(@PathParam("id") Long id) {	
		OperatingSystem operatingSystem = operatingSystemManager.findById(id);
		
		if(operatingSystem == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "No operatingSystem to id : " + id))
					.build();
		}
		
		return Response.ok().entity(operatingSystem).build();
	}
	
	@PUT
	@Path("")
	public Response createOperatingSystem(OperatingSystem operatingSystem) {
		if(operatingSystem.getName() == null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'name' property is missing"))
					.build();		
		}
		
		if(operatingSystem.getNameFolder() == null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'nameFolder' property is missing"))
					.build();		
		}
		
		
		if(operatingSystemManager.findByName(operatingSystem.getName()) != null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "OperatingSystem '" + operatingSystem.getName() + "' already use"))
					.build();
		}
		
		//Delete the setup of the id by the user
		operatingSystem.setId(0);
		
		operatingSystemManager.addOperatingSystem(operatingSystem);
		
		JsonObject jsonResponse = Json.createObjectBuilder().add("id", operatingSystem.getId()).build();
		
		return Response.ok().entity(jsonResponse).build();
	}
	
	@POST
	@Path("{id:[0-9]+}")
	public Response updateOperatingSystem(@PathParam("id") Long id, OperatingSystem operatingSystem) {
		if(operatingSystemManager.findById(id) == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "OperatingSystem with id '" + id + "' no exist"))
					.build();
		}
		
		if(id != operatingSystem.getId()) {
			operatingSystem.setId(id);
		}
		
		operatingSystemManager.updateOperatingSystem(operatingSystem);
		
		return Response.ok().entity(operatingSystemManager.findById(id)).build();
	}

	@DELETE
	@Path("{id:[0-9]+}")
	public Response deleteOperatingsystem(@PathParam("id") Long id) {
		if(operatingSystemManager.findById(id) == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "OperatingSystem with id '" + id + "' no exist"))
					.build();
		}
		
		operatingSystemManager.removeOperatingSystem(operatingSystemManager.findById(id));
		
		return Response.ok().build();
	}
}

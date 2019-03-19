package fr.amu.ter_genrest.controllers;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.amu.ter_genrest.controllers.utils.Utils;
import fr.amu.ter_genrest.entities.environment_technical.OperatingSystem;
import fr.amu.ter_genrest.services.environment_technical.OperatingSystemManager;

@Path("api/operatingSystem")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OperatingSystemsManagerControllerRest {


	@EJB
	private OperatingSystemManager operatingSystemManager;

	public OperatingSystemsManagerControllerRest() {}

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


	@POST
	@Path("{id:[0-9]+}")
	public Response updateOperatingSystem(@PathParam("id") Long id, OperatingSystem operatingSystem) {

		OperatingSystem operatingSystemFinded = operatingSystemManager.findById(id);

		if(operatingSystemFinded == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "OperatingSystem with id '" + id + "' no exist"))
					.build();
		}

		if(operatingSystem.getName() != null) {
			operatingSystemFinded.setName(operatingSystem.getName());
		}

		if(operatingSystem.getNameFolder() != null) {
			operatingSystemFinded.setNameFolder(operatingSystem.getNameFolder());
		}
		operatingSystemFinded = operatingSystemManager.updateOperatingSystem(operatingSystemFinded);		
		return Response.ok().entity(operatingSystemFinded).build();
	}
}

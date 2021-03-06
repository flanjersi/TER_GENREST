package fr.amu.ter_genrest.controllers;

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

import fr.amu.ter_genrest.controllers.utils.Utils;
import fr.amu.ter_genrest.entities.project_specifications.Actuator;
import fr.amu.ter_genrest.services.project_specifications.ActuatorManager;

@Path("api/actuators")

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActuatorManagerControllerREST {

	@EJB
	private ActuatorManager actuatorManager;

	public ActuatorManagerControllerREST() {
	}

	@GET
	public Response getActuators() {
		List<Actuator> actuators = actuatorManager.findAll();
		return Response.ok().entity(actuators).build();
	}

	@GET
	@Path("/{id:[0-9]+}")
	public Response getActuatorById(@PathParam("id") Long id) {
		Actuator actuatortoFind = actuatorManager.findActuator(id);
		if (actuatortoFind == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "No actuator to id : " + id)).build();
		}
		return Response.ok().entity(actuatortoFind).build();
	}

	@POST
	@Path("/{id:[0-9]+}")
	public Response updateActuator(@PathParam("id") Long id, Actuator actuator) {
		Actuator actuatortoFind = actuatorManager.findActuator(id);

		if (actuatortoFind == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "No actuator with id : " + id)).build();
		}

		if (actuator.getName() != null) {
			actuatortoFind.setName(actuator.getName());
		}

		if (actuator.getLatitude() != 0) {
			actuatortoFind.setLatitude(actuator.getLatitude());
		}

		if (actuator.getLongitude() != 0) {
			actuatortoFind.setLongitude(actuator.getLongitude());
		}

		if (actuator.getModel() != null) {
			actuatortoFind.setModel(actuator.getModel());
		}
		
		//TODO Test if name already exist

		actuatortoFind = actuatorManager.updateActuator(actuatortoFind);

		return Response.ok().entity(actuatortoFind).build();
	}

}

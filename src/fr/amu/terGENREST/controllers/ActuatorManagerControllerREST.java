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
import fr.amu.terGENREST.entities.projectSpecifications.Actuator;
import fr.amu.terGENREST.services.projectSpecifications.ActuatorManager;

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

		if (actuator.getBrand() != null) {
			actuatortoFind.setBrand(actuator.getBrand());

		}

		if (actuator.getLatitude() != 0) {
			actuatortoFind.setLatitude(actuator.getLatitude());
		}

		if (actuator.getLongitude() != 0) {
			actuatortoFind.setLatitude(actuator.getLongitude());

		}

		if (actuator.getReference() != null) {
			actuatortoFind.setReference(actuator.getModel());

		}
		if (actuator.getModel() != null) {
			actuatortoFind.setModel(actuator.getModel());

		}
		if (actuator.getState() != null) {
			actuatortoFind.setState(actuator.getState());

		}
		
		actuatortoFind = actuatorManager.updateActuator(actuatortoFind);
		
		return Response.ok().entity(actuatortoFind).build();

	}

}

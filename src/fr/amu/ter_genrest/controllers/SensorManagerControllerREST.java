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
import fr.amu.ter_genrest.entities.project_specifications.Sensor;
import fr.amu.ter_genrest.services.project_specifications.SensorManager;

@Path("api/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorManagerControllerREST {

	@EJB
	private SensorManager sensorManager;

	@GET
	@Path("{id:[0-9]+}")
	public Response getSensorById(@PathParam("id") Long id) {
		Sensor sensor = sensorManager.findSensor(id);

		if(sensor == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Sensor with id '" + id + "' not found"))
					.build();
		}
		return Response.ok().entity(sensor).build();
	}

	@POST
	@Path("{id:[0-9]+}")
	public Response updateSensor(@PathParam("id") Long id, Sensor sensor) {

		Sensor sensorFinded = sensorManager.findSensor(id);

		if(sensorFinded == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Sensor with id '" + id + "' not found"))
					.build();
		}

		if(sensor.getLatitude() != 0) {
			sensorFinded.setLatitude(sensor.getLatitude());
		}

		if(sensor.getLongitude() != 0) {
			sensorFinded.setLongitude(sensor.getLongitude());
		}

		if(sensor.getUnitData() != null) {
			sensorFinded.setUnitData(sensor.getUnitData());
		}

		if(sensor.getState() != null) {
			sensorFinded.setState(sensor.getState());
		}

		if(sensor.getReference() != null) {
			sensorFinded.setReference(sensor.getReference());
		}

		if(sensor.getBrand() != null) {
			sensorFinded.setBrand(sensor.getBrand());
		}

		if(sensor.getModel() != null) {
			sensorFinded.setModel(sensor.getModel());
		}

		sensorFinded = sensorManager.updateSensor(sensorFinded);

		return Response.ok().entity(sensorFinded).build();
	}
}

package fr.amu.ter_genrest.controllers;

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

import fr.amu.ter_genrest.controllers.utils.Utils;
import fr.amu.ter_genrest.entities.project_specifications.Actuator;
import fr.amu.ter_genrest.entities.project_specifications.Corridor;
import fr.amu.ter_genrest.entities.project_specifications.Room;
import fr.amu.ter_genrest.entities.project_specifications.Sensor;
import fr.amu.ter_genrest.services.project_specifications.RoomManager;

@Path("/api/rooms/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomManagerControllerREST {

	@EJB
	private RoomManager roomManager;

	public RoomManagerControllerREST() {

	}

	@GET
	public Response getAllRooms() {
		List<Room> rooms = roomManager.findAllRooms();
		return Response.ok().entity(rooms).build();
	}

	@GET
	@Path("{id:[0-9]+}")
	public Response getRoomById(@PathParam("id") Long id) {

		Room room = roomManager.findRoom(id);
		if (room == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "No room with id : " + id)).build();
		}
		return Response.ok().entity(room).build();

	}

	@POST
	@Path("{id:[0-9]+}")
	public Response updateRoom(@PathParam("id") Long id, Room room) {
		Room roomTofind = roomManager.findRoom(id);

		if (roomTofind == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, " Room with" + id + "not found")).build();
		}

		if (room.getNumberRoom() != 0) {
			roomTofind.setNumberRoom(room.getNumberRoom());
		}

		if (room.getType() != null) {
			roomTofind.setType(room.getType());
		}
		roomTofind = roomManager.updateRoom(roomTofind);
		return Response.ok().entity(roomTofind).build();
	
	}

	@PUT
	@Path("{idRoom:[0-9]+}/sensors/")
	public Response addSensor(@PathParam("idRoom") Long idRoom, Sensor sensor) {

		Room roomTofind = roomManager.findRoom(idRoom);

		if (roomTofind == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "No room with id : " + idRoom)).build();
		}

		if (sensor.getBrand() == null) {
			return Response.status(400).entity(Utils.makeErrorMessage(404, "Sensor brand is missing")).build();
		}
		
		if (sensor.getLatitude() == 0) {
			return Response.status(400).entity(Utils.makeErrorMessage(404, "Sensor latitude is missing")).build();
		}

		if (sensor.getLongitude() == 0) {
			return Response.status(400).entity(Utils.makeErrorMessage(404, "Sensor longitude is missing")).build();
		}

		if (sensor.getModel() == null) {
			return Response.status(400).entity(Utils.makeErrorMessage(404, "Sensor model is missing")).build();
		}
		if (sensor.getReference() == null) {
			return Response.status(400).entity(Utils.makeErrorMessage(404, "Sensor reference is missing")).build();
		}
		
		if (sensor.getState() == null) {
			return Response.status(400).entity(Utils.makeErrorMessage(404, "Sensor state is missing")).build();
		}

		if (sensor.getUnitData() == null) {
			return Response.status(400).entity(Utils.makeErrorMessage(404, "Sensor unitdata is missing")).build();
		}

		roomTofind.addSensor(sensor);
		roomTofind = roomManager.updateRoom(roomTofind);

		Optional<Sensor> addedSensor = roomTofind.getSensors().stream()
				.max((b1, b2) -> Long.compare(b1.getId(), b2.getId()));

		JsonObject jsonResponse = Json.createObjectBuilder().add("id", addedSensor.get().getId()).build();
		return Response.status(201).entity(jsonResponse).build();
	}

	@PUT
	@Path("{idRoom:[0-9]+}/actuators/")
	public Response addActuator(@PathParam("idRoom") Long idRoom, Actuator actuator) {

		Room roomTofind = roomManager.findRoom(idRoom);

		if (roomTofind == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "No room with id : " + idRoom)).build();
		}

		if (actuator.getBrand() == null) {
			return Response.status(400).entity(Utils.makeErrorMessage(400, "'Brand' property is missing")).build();
		}

		if (actuator.getModel() == null) {
			return Response.status(400).entity(Utils.makeErrorMessage(400, "'Model' property is missing")).build();
		}

		if (actuator.getReference() == null) {
			return Response.status(400).entity(Utils.makeErrorMessage(400, "'Reference' property is missing")).build();
		}
		if (actuator.getState() == null) {
			return Response.status(400).entity(Utils.makeErrorMessage(400, "'State' property is missing")).build();
		}
		if (actuator.getLongitude() == 0) {
			return Response.status(400).entity(Utils.makeErrorMessage(400, "'Longitude' property is missing")).build();
		}
		if (actuator.getLatitude() == 0) {
			return Response.status(400).entity(Utils.makeErrorMessage(400, "'Latitude' property is missing")).build();
		}

		roomTofind.addActuator(actuator);

		roomTofind = roomManager.updateRoom(roomTofind);

		Optional<Actuator> addedActuator = roomTofind.getActuators().stream()
				.max((b1, b2) -> Long.compare(b1.getId(), b2.getId()));

		JsonObject jsonResponse = Json.createObjectBuilder().add("id", addedActuator.get().getId()).build();

		return Response.status(201).entity(jsonResponse).build();
	}

	@DELETE
	@Path("{idRoom:[0-9]+}/sensors/{idSensor:[0-9]+}")
	public Response removeSensor(@PathParam("idRoom") Long idRoom, @PathParam("idSensor") Long idSensor) {

		Room roomTofind = roomManager.findRoom(idRoom);

		if (roomTofind == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "No room with id : " + idRoom)).build();
		}

		Optional<Sensor> sensorTofind = roomTofind.getSensors().stream().filter(s -> s.getId().equals(idSensor)).findFirst();

		if (!sensorTofind.isPresent()) {
			return Response.status(404)
					.entity(Utils.makeErrorMessage(404, "Sensor with id '" + idSensor + "' not found")).build();
		}

		roomTofind.removeSensor(sensorTofind.get());
		roomManager.updateRoom(roomTofind);

		return Response.ok().build();
		
	}

	@DELETE
	@Path("{idRoom:[0-9]+}/actuators/{idActuator:[0-9]+}")
	public Response removeActuator(@PathParam("idRoom") Long idRoom, @PathParam("idActuator") Long idActuator) {

		Room roomTofind = roomManager.findRoom(idRoom);

		if (roomTofind == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "No room with id : " + idRoom)).build();
		}

		Optional<Actuator> actuatorTofind = roomTofind.getActuators().stream().filter(a -> a.getId().equals(idActuator))
				.findFirst();
		if (!actuatorTofind.isPresent()) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "Added Actuator not found")).build();
		}

		roomTofind.removeActuator(actuatorTofind.get());
		roomManager.updateRoom(roomTofind);

		return Response.ok().build();

	}

}

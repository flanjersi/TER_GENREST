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
import fr.amu.terGENREST.entities.projectSpecifications.Room;
import fr.amu.terGENREST.services.projectSpecifications.RoomManager;

@Path("/rooms")
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
	@Path("/{id:[0-9]+}")
	public Response getRoomById(@PathParam("id") Long id) {

		Room room = roomManager.findRoom(id);
		if (room == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "No room with id : " + id)).build();
		}
		return Response.ok().entity(room).build();

	}

	@POST
	@Path("/{id:[0-9]+}")
	public Response updateRoom(@PathParam("id") Long id, Room room) {

		//TODO Changement vers la modification plus propre -> modification si != null
		
		
		if (room.getNumberRoom() == 0) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, " 'NumberRoom' property is missing"))
					.build();
		}

		if (room.getType() == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, " 'TyprRoom' property is missing")).build();

		}

		roomManager.updateRoom(room);
		return Response.ok().entity(roomManager.findRoom(id)).build();

	}
	
	//TODO Création d'une méthode d'ajout de sensor (note à moi même)
	//TODO Création d'une méthode d'ajout de actuator
	//TODO Création d'une méthode de suppression de sensor (note à moi même)
	//TODO Création d'une méthode de suppression de actuator

}

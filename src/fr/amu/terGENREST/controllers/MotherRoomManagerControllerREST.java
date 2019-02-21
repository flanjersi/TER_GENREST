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
import fr.amu.terGENREST.entities.projectSpecifications.Corridor;
import fr.amu.terGENREST.entities.projectSpecifications.MotherRoom;
import fr.amu.terGENREST.entities.projectSpecifications.Room;
import fr.amu.terGENREST.services.projectSpecifications.MotherRooomManager;

@Path("api/motherrooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MotherRoomManagerControllerREST {

	@EJB
	private MotherRooomManager motherRoomManager;

	public MotherRoomManagerControllerREST() {

	}

	@GET
	public Response getAllMotherRooms() {

		List<MotherRoom> motherRoooms = motherRoomManager.findAllMotherRoom();
		return Response.ok().entity(motherRoooms).build();
	}

	@GET
	@Path("/{id:[0-9]+}")
	public Response getMotherRoomById(@PathParam("id") Long id) {
		MotherRoom motherRoom = motherRoomManager.findById(id);
		if (motherRoom == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "No mother room to id : " + id)).build();
		}
		return Response.ok().entity(motherRoom).build();
	}

	@POST
	@Path("/{id:[0-9]+}")
	public Response updateMotherRoom(@PathParam("id") Long id, MotherRoom motherRoom) {

		// TODO Changement vers la modification plus propre -> modification si != null
		// (DONE)

		MotherRoom motherRoomToFind = motherRoomManager.findById(id);

		if (motherRoomToFind == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, " MotherRoom with id: " + id + "not found"))
					.build();
		}

		if (motherRoom.getType() != null) {
			motherRoomToFind.setType(motherRoom.getType());
		}
		if (motherRoom.getNumberMotherRoom() != 0) {
			motherRoomToFind.setNumberMotherRoom(motherRoom.getNumberMotherRoom());

		}

		motherRoomManager.updateMotherRoom(motherRoom);
		return Response.ok().entity(motherRoomManager.findById(id)).build();
	}

	// TODO Cr�ation d'une m�thode d'ajout de room (DONE)

	@PUT
	@Path("/{idMotherroom:[0-9]+}/rooms/")
	public Response addRoom(@PathParam("idMotherroom") Long idMotherroom, Room room) {

		MotherRoom motherRoomToFind = motherRoomManager.findById(idMotherroom);

		if (motherRoomToFind == null) {
			return Response.status(404)
					.entity(Utils.makeErrorMessage(404, " MotherRoom with id: " + idMotherroom + "not found")).build();
		}
		if (room.getNumberRoom() == 0) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, " 'NumberRoom' is missing")).build();

		}

		if (room.getType() == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, " 'Type' is missing")).build();

		}

		motherRoomToFind.addRoom(room);
		motherRoomManager.updateMotherRoom(motherRoomToFind);

		motherRoomToFind = motherRoomManager.findById(motherRoomToFind.getId());

		Optional<Room> roomToadd = motherRoomToFind.getListRoom().stream()
				.max((r1, r2) -> Long.compare(r1.getId(), r2.getId()));

		if (roomToadd.isPresent()) {
			JsonObject jsonResponse = Json.createObjectBuilder().add("id", roomToadd.get().getId()).build();
			return Response.status(201).entity(jsonResponse).build();

		}

		else {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "Room added not found")).build();
		}

	}

	// TODO Cr�ation d'une m�thode d'ajout de corridor (DONE)
	@PUT
	@Path("/{idMotherroom:[0-9]+}/corridors/")
	public Response addCorridor(@PathParam("idMotherroom") Long idMotherroom, Corridor corridor) {

		MotherRoom motherRoomToFind = motherRoomManager.findById(idMotherroom);

		if (motherRoomToFind == null) {
			return Response.status(404)
					.entity(Utils.makeErrorMessage(404, " MotherRoom with id: " + idMotherroom + "not found")).build();
		}

		if (corridor.getNumberCorridor() == 0) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, " 'NumberCorrdidor' is missing")).build();

		}

		motherRoomToFind.addCorridor(corridor);
		motherRoomManager.updateMotherRoom(motherRoomToFind);

		motherRoomToFind = motherRoomManager.findById(motherRoomToFind.getId());

		Optional<Corridor> corridorToadd = motherRoomToFind.getListCorridor().stream()
				.max((r1, r2) -> Long.compare(r1.getId(), r2.getId()));

		if (corridorToadd.isPresent()) {
			JsonObject jsonResponse = Json.createObjectBuilder().add("id", corridorToadd.get().getId()).build();
			return Response.status(201).entity(jsonResponse).build();

		}

		else {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "Added corridor not found")).build();
		}

	}

	// TODO Cr�ation d'une m�thode de suppression de room (DONE)
	@DELETE
	@Path("{idMotherroom:[0-9]+}/rooms/{idRoom:[0-9]+}")
	public Response removeRoom(@PathParam("idMotherroom") Long idMotherroom, @PathParam("idRoom") Long idRoom) {

		MotherRoom motehrRoomTofind = motherRoomManager.findById(idRoom);

		if (motehrRoomTofind == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "No MotherRoom with id : " + idRoom))
					.build();
		}
		Optional<Room> roomToremove = motehrRoomTofind.getListRoom().stream().filter(r -> r.getId() == idRoom)
				.findFirst();

		if (roomToremove.isPresent()) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "Room with id '" + idRoom + "' not found"))
					.build();
		}

		motehrRoomTofind.removeRoom(roomToremove.get());
		motherRoomManager.updateMotherRoom(motehrRoomTofind);
		return Response.ok().build();

	}

// TODO Cr�ation d'une m�thode de suppression de corridor (DONE)
	@DELETE
	@Path("{idMotherroom:[0-9]+}/corridors/{idCorridor:[0-9]+}")
	public Response removeCorridor(@PathParam("idMotherroom") Long idMotherroom,
			@PathParam("idCorridor") Long idCorridor) {

		MotherRoom motehrRoomTofind = motherRoomManager.findById(idCorridor);

		if (motehrRoomTofind == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "No MotherRoom with id : " + idCorridor))
					.build();
		}
		Optional<Corridor> corridorToremove = motehrRoomTofind.getListCorridor().stream()
				.filter(c -> c.getId() == idCorridor).findFirst();

		if (corridorToremove.isPresent()) {
			return Response.status(404)
					.entity(Utils.makeErrorMessage(404, "Corridor with id '" + idCorridor + "' not found")).build();
		}

		motehrRoomTofind.removeCorridor(corridorToremove.get());
		motherRoomManager.updateMotherRoom(motehrRoomTofind);

		return Response.ok().build();

	}

}

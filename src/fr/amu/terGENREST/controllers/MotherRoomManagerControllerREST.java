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
import fr.amu.terGENREST.entities.projectSpecifications.MotherRoom;
import fr.amu.terGENREST.services.projectSpecifications.MotherRooomManager;

@Path("api/buildings/{idBuilding:[0-9]+}/floors/{idFloor: [0-9]+}/motherrooms")
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
			return Response.status(404).entity(Utils.makeErrorMessage(404, "No Corridor to id : " + id)).build();
		}
		return Response.ok().entity(motherRoom).build();
	}

	@POST
	@Path("/{id:[0-9]+}")
	public Response updateMotherRoom(@PathParam("id") Long id, MotherRoom motherRoom) {

		if (motherRoom.getNumberMotherRoom() == 0) {
			return Response.status(403).entity(Utils.makeErrorMessage(404, " 'NumberMotherRoom' property is missing"))
					.build();
		}

		if (motherRoom.getType() == null) {
			return Response.status(403).entity(Utils.makeErrorMessage(404, " 'MotherRoomType' property is missing"))
					.build();
		}
		
		motherRoomManager.updateMotherRoom(motherRoom);
		return Response.ok().entity(motherRoomManager.findById(id)).build();
	}

}

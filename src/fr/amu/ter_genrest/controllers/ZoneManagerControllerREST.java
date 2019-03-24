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
import fr.amu.ter_genrest.entities.project_specifications.Corridor;
import fr.amu.ter_genrest.entities.project_specifications.Zone;
import fr.amu.ter_genrest.entities.project_specifications.Room;
import fr.amu.ter_genrest.services.project_specifications.ZoneManager;
import fr.amu.ter_genrest.services.project_specifications.RoomManager;

@Path("api/zones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ZoneManagerControllerREST {

	@EJB
	private ZoneManager zoneManager;

	public ZoneManagerControllerREST() {
	}

	@GET
	@Path("")
	public Response getAllZones() {
		List<Zone> zones = zoneManager.findAllZone();
		return Response.ok().entity(zones).build();
	}

	@GET
	@Path("/{id:[0-9]+}")
	public Response getZoneById(@PathParam("id") Long id) {
		Zone zone = zoneManager.findById(id);
		if (zone == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "No zone to id : " + id)).build();
		}
		return Response.ok().entity(zone).build();
	}

	@POST
	@Path("/{id:[0-9]+}")
	public Response updateZone(@PathParam("id") Long id, Zone zone) {

		Zone zoneToFind = zoneManager.findById(id);

		if (zoneToFind == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "Zone with id: " + id + "not found"))
					.build();
		}

		if (zone.getType() != null) {
			zoneToFind.setType(zone.getType());
		}

		if (zone.getName() != null) {
			zoneToFind.setName(zone.getName());
		}
		
		zoneManager.updateZone(zoneToFind);

		return Response.ok().entity(zoneManager.findById(id)).build();
	}

	@PUT
	@Path("/{idZone:[0-9]+}/rooms/")
	public Response addRoom(@PathParam("idZone") Long idZone, Room room) {

		Zone zoneToFind = zoneManager.findById(idZone);

		if (zoneToFind == null) {
			return Response.status(404)
					.entity(Utils.makeErrorMessage(404, " Zone with id: " + idZone + "not found")).build();
		}

		if (room.getName() == null) {
			return Response.status(400).entity(Utils.makeErrorMessage(400, " 'name' is missing")).build();
		}

		if (room.getType() == null) {
			return Response.status(400).entity(Utils.makeErrorMessage(400, " 'type' is missing")).build();
		}

		zoneToFind.addRoom(room);
		zoneToFind = zoneManager.updateZone(zoneToFind);

		Optional<Room> roomToadd = zoneToFind.getRooms().stream()
				.max((r1, r2) -> Long.compare(r1.getId(), r2.getId()));

		JsonObject jsonResponse = Json.createObjectBuilder().add("id", roomToadd.get().getId()).build();
		return Response.status(201).entity(jsonResponse).build();
	}

	@PUT
	@Path("/{idZone:[0-9]+}/corridors/")
	public Response addCorridor(@PathParam("idZone") Long idZone, Corridor corridor) {

		Zone zoneToFind = zoneManager.findById(idZone);

		if (zoneToFind == null) {
			return Response.status(404)
					.entity(Utils.makeErrorMessage(404, "Zone with id: " + idZone + "not found")).build();
		}

		if (corridor.getName() == null) {
			return Response.status(400).entity(Utils.makeErrorMessage(400, " 'name' is missing")).build();
		}

		zoneToFind.addCorridor(corridor);
		zoneManager.updateZone(zoneToFind);

		zoneToFind = zoneManager.findById(zoneToFind.getId());
		Optional<Corridor> corridorToadd = zoneToFind.getCorridors().stream()
				.max((r1, r2) -> Long.compare(r1.getId(), r2.getId()));

		JsonObject jsonResponse = Json.createObjectBuilder().add("id", corridorToadd.get().getId()).build();
		return Response.status(201).entity(jsonResponse).build();
	}

	@DELETE
	@Path("{idZone:[0-9]+}/rooms/{idRoom:[0-9]+}")
	public Response removeRoom(@PathParam("idZone") Long idZone, @PathParam("idRoom") Long idRoom) {

		Zone zoneToFind = zoneManager.findById(idZone);

		if (zoneToFind == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "No zone with id : " + idRoom))
					.build();
		}

		Optional<Room> roomToremove = zoneToFind.getRooms().stream().filter(r -> r.getId().equals(idRoom))
				.findFirst();

		if (!roomToremove.isPresent()) {
			return Response.status(404)
					.entity(Utils.makeErrorMessage(404, "Room with id '" + idRoom + "' not found")).build();
		}

		zoneToFind.removeRoom(roomToremove.get());
		zoneManager.updateZone(zoneToFind);

		return Response.ok().build();
	}

	@DELETE
	@Path("{idZone:[0-9]+}/corridors/{idCorridor:[0-9]+}")
	public Response removeCorridor(@PathParam("idZone") Long idZone,
			@PathParam("idCorridor") Long idCorridor) {

		Zone zoneToFind = zoneManager.findById(idZone);

		if (zoneToFind == null) {
			return Response.status(404).entity(Utils.makeErrorMessage(404, "No zone with id : " + idCorridor))
					.build();
		}

		Optional<Corridor> corridorToremove = zoneToFind.getCorridors().stream()
				.filter(c -> c.getId().equals(idCorridor)).findFirst();

		if (!corridorToremove.isPresent()) {
			return Response.status(404)
					.entity(Utils.makeErrorMessage(404, "Corridor with id '" + idCorridor + "' not found")).build();
		}

		zoneToFind.removeCorridor(corridorToremove.get());
		zoneManager.updateZone(zoneToFind);

		return Response.ok().build();
	}
}
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
import fr.amu.terGENREST.entities.projectSpecifications.Floor;
import fr.amu.terGENREST.entities.projectSpecifications.MotherRoom;
import fr.amu.terGENREST.services.projectSpecifications.FloorManager;


@Path("api/floors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FloorManagerControllerREST {

	@EJB
	private FloorManager floorManager;

	public FloorManagerControllerREST() {}

	@GET
	@Path("")
	public Response getAllFloors() {
		List<Floor>floors = floorManager.findAllFloor();
		return Response.ok().entity(floors).build();
	}

	@GET
	@Path("/{id:[0-9]+}")
	public Response getFloorById(@PathParam("id") Long id) {	
		Floor floor = floorManager.findById(id);

		if(floor == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "No floor with id : " + id))
					.build();
		}

		return Response.ok().entity(floor).build();
	}
	
	@POST
	@Path("/{idFloor:[0-9]+}")
	public Response updateFloor(@PathParam("idFloor") Long idFloor, Floor floor) {

		Floor floorFinded = floorManager.findById(idFloor);
		
		if(floorFinded == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Floor with id '" + idFloor + "' no exist"))
					.build();
		}
	
		if(floor.getFloorNumber() != 0) {
			floorFinded.setFloorNumber(floor.getFloorNumber());
		}
		
		
		floorFinded = floorManager.updateFloor(floorFinded);

		return Response.ok().entity(floorFinded).build();
	}
	
	@PUT
	@Path("/{idFloor:[0-9]+}/corridors")
	public Response createCorridor(@PathParam("idFloor") Long idFloor, Corridor corridor) {
		Floor floor = floorManager.findById(idFloor );
		
		if(floor == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "floor with id '" + idFloor + "' no exist"))
					.build();
		}
		
		if(floor.getFloorNumber() == 0) {
			return  Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'FloorNumber' property is missing"))
					.build();
		}

		floor.addCorridor(corridor);
		floorManager.updateFloor(floor);
		
		floor = floorManager.findById(floor.getId());
		
		Optional<Corridor> corridoeAdded = floor.getCorridors()
				.stream()
				.filter(floorCorridor -> (floorCorridor.getNumberCorridor()==(corridor.getNumberCorridor())))
				.findFirst();

		JsonObject jsonResponse = Json.createObjectBuilder().add("id", corridoeAdded.get().getId()).build();

		return Response.ok().entity(jsonResponse).build();
	}
	
	@PUT
	@Path("/{idFloor:[0-9]+}/motherRooms")
	public Response createMotherRoom(@PathParam("idFloor") Long idFloor, MotherRoom motherRoom) {
		Floor floor = floorManager.findById(idFloor );
		
		if(floor == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "floor with id '" + idFloor + "' no exist"))
					.build();
		}
		
		if(floor.getFloorNumber() == 0) {
			return  Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'FloorNumber' property is missing"))
					.build();
		}

		floor.addMotherRoom(motherRoom);
		floorManager.updateFloor(floor);
		
		floor = floorManager.findById(floor.getId());
		
		Optional<MotherRoom> motherRoomAdded = floor.getBuildingMotherRoom()
				.stream()
				.filter(floorMotherRoom -> (floorMotherRoom.getType().equals(motherRoom.getType())))
				.findFirst();

		JsonObject jsonResponse = Json.createObjectBuilder().add("id", motherRoomAdded.get().getId()).build();

		return Response.ok().entity(jsonResponse).build();
	}
	
	@DELETE
	@Path("/{idFloor:[0-9]+}/corridors/{id:[0-9]+}")
	public Response deleteCorridor(@PathParam("idFloor") Long idFloor, @PathParam("id") Long idCorridor) {
		Floor floor = floorManager.findById(idFloor );

		if(floor == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Floor id '" + idFloor + "' no exist"))
					.build();
		}

		Optional<Corridor> motherRoomRemoved = floor.getCorridors()
				.stream().filter(c -> c.getId() == idCorridor).findFirst();

		if(!motherRoomRemoved.isPresent()) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "corridor with id '" + idCorridor + "' not found"))
					.build();
		}

		floor.removeCorridor(motherRoomRemoved.get());
		floorManager.updateFloor(floor);

		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{idFloor:[0-9]+}/corridors/{id:[0-9]+}")
	public Response deleteMotherRoom(@PathParam("idFloor") Long idFloor, @PathParam("id") Long idMotherRoom) {
		Floor floor = floorManager.findById(idFloor );

		if(floor == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Floor id '" + idFloor + "' no exist"))
					.build();
		}

		Optional<MotherRoom> motherRoomRemoved = floor.getBuildingMotherRoom()
				.stream().filter(c -> c.getId() == idMotherRoom).findFirst();

		if(!motherRoomRemoved.isPresent()) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "motherRoom with id '" + idMotherRoom + "' not found"))
					.build();
		}

		floor.removeMotherRoom(motherRoomRemoved.get());
		floorManager.updateFloor(floor);

		return Response.ok().build();
	}
}

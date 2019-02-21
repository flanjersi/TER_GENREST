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
import fr.amu.terGENREST.entities.projectSpecifications.Building;
import fr.amu.terGENREST.entities.projectSpecifications.Floor;
import fr.amu.terGENREST.services.project.ProjectManager;
import fr.amu.terGENREST.services.projectSpecifications.BuildingManager;
import fr.amu.terGENREST.services.projectSpecifications.FloorManager;
import fr.amu.terGENREST.services.user.UserManager;

@Path("api/buildings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class BuildingManagerControllerREST {

	@EJB
	private BuildingManager buildingManager;

	@EJB
	private FloorManager floorManager;

	@EJB
	private ProjectManager projectManager;

	@EJB
	private UserManager userManager;

	@GET
	@Path("")
	public Response getAllBuildings() {
		List<Building> buildings = buildingManager.findAllBuilding();
		return Response.ok().entity(buildings).build();
	}


	@GET
	@Path("/{id:[0-9]+}")
	public Response getBuildingById(@PathParam("id") Long id) {	
		Building building = buildingManager.findById(id);

		if(building == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "No building with id : " + id))
					.build();
		}

		return Response.ok().entity(building).build();
	}

	@POST
	@Path("/{idBuilding:[0-9]+}")
	public Response updateBuilding(@PathParam("idBuilding") Long idBuilding, Building building) {

		Building buildingFinded = buildingManager.findById(idBuilding);
		
		if(buildingFinded == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Building with id" + idBuilding + "no exist"))
					.build();
		}
	
		if(building.getType() != null) {
			buildingFinded.setType(building.getType());
		}
		
		if(building.getAddress() != null) {
			buildingFinded.setAddress(building.getAddress());
		}
		
		buildingFinded = buildingManager.updateBuilding(buildingFinded);

		return Response.ok().entity(buildingFinded).build();
	}
	

	@PUT
	@Path("/{idBuilding:[0-9]+}/floors")
	public Response createFloor(@PathParam("idBuilding") Long idBuilding, Floor floor) {
		
		Building building = buildingManager.findById(idBuilding);
		
		if(building == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Building with id" + idBuilding + "no exist"))
					.build();
		}
		
		if(floor.getFloorNumber() == 0) {
			return  Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "FloorNumber property is missing"))
					.build();
		}
		
		Optional<Floor> floorsearch = building.getFloors()
				.stream()
				.filter(floorad -> floorad.getFloorNumber() == floor.getFloorNumber())
				.findFirst();
		
		if(floorsearch.isPresent()) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "floorNumber" + floor.getFloorNumber() + " already exist"))
					.build();
		}

		building.addFloor(floor);
		buildingManager.updateBuilding(building);
		
		building = buildingManager.findById(building.getId());
		
		Optional<Floor> floorAdded = building.getFloors()
				.stream()
				.filter(buildingFloor -> (buildingFloor.getFloorNumber()==(floor.getFloorNumber())))
				.findFirst();

		JsonObject jsonResponse = Json.createObjectBuilder().add("id", floorAdded.get().getId()).build();

		return Response.status(201).entity(jsonResponse).build();	
	}
	
	@DELETE
	@Path("/{idBuilding:[0-9]+}/floors/{id:[0-9]+}")
	public Response deleteFloor(@PathParam("idBuilding") Long idBuilding, @PathParam("id") Long idFloor) {
		Building building = buildingManager.findById(idBuilding );

		if(building == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "building id" + idBuilding + " no exist"))
					.build();
		}


		Optional<Floor> floorRemoved = building.getFloors()
				.stream().filter(c -> c.getId().equals(idFloor)).findFirst();

		if(!floorRemoved.isPresent()) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "floor with id " + idFloor + " not found"))
					.build();
		}

		building.removeFloor(floorRemoved.get());
		buildingManager.updateBuilding(building);

		return Response.ok().build();
	}
}
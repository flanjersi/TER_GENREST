package fr.amu.terGENREST.controllers;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

<<<<<<< HEAD
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;

=======
>>>>>>> a38c21e19997bda637ce4e05c23be1d1b058a60d
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

<<<<<<< HEAD
	
=======


>>>>>>> a38c21e19997bda637ce4e05c23be1d1b058a60d
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


<<<<<<< HEAD
	@PUT
	@Path("")
	public Response createBuilding(@PathParam("idProject") Long idProject, Building building,@PathParam("idUser") Long idUser) {
		

		if(userManager.findUser(idUser) == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(400, "User with id '" + idUser + "' no exist"))
					.build();
		}
		List<Project> projects = userManager.findUser(idUser).getProjects();
		
		long nbProject = projects
				.stream()
				.filter(projectUser -> (projectUser.getId().equals(idProject)))
				.count();
		
		if(nbProject == 0) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(400, "Project with id '" + idProject + "' no exist"))
					.build();
		}
			
		if(building.getType() == null) {
			return Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'type' property is missing"))
					.build();		
		}
		
		Project p = projectManager.findProject(idProject);
       
		p.addBuilding(building);
		
		projectManager.updateProject(p);
		
		Optional<Building> buildingAdded = p.getBuilding()
				.stream()
				.filter(projectUser -> (projectUser.getType().equals(building.getType())))
				.findFirst();
		
		JsonObject jsonResponse = Json.createObjectBuilder().add("id", buildingAdded.get().getId()).build();
		return Response.ok().entity(jsonResponse).build();
		}
	
	@POST
	@Path("/{id:[0-9]+}")
	public Response updateBuilding(@PathParam("id") Long id,@PathParam("idUser") Long idUser,@PathParam("idProject") Long idProject, Building buildingUpdated) {
		
		if(userManager.findUser(idUser) == null) {

			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(400, "User with id '" + idUser + "' no exist"))
					.build();
		}

		
		if(projectManager.findProject(idProject) == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(400, "Project with id '" + idProject + "' no exist"))
					.build();
		}
		

		long nbBuilding = projectManager.findProject(idProject).getBuilding()
				.stream()
				.filter(buildingProject -> (buildingProject.getId() == buildingUpdated.getId()))
				.count();
		
		if(nbBuilding == 0) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(400, "Building with id '" + buildingUpdated.getId() + "' no exist"))
					.build();
		}		
		Building building = buildingManager.updateBuilding(buildingUpdated);
		List<Building> buildingsResearch = projectManager.findProject(idProject).getBuilding();		
		Optional<Building> buildingResearch = buildingsResearch.stream().filter(buildingFinded -> buildingFinded.equals(buildingUpdated)).findFirst();
				
		return Response.status(200).entity(buildingResearch).build();
=======
	@POST
	@Path("/{idBuilding:[0-9]+}")
	public Response updateBuilding(@PathParam("idBuilding") Long idBuilding, Building building) {

		Building buildingFinded = buildingManager.findById(idBuilding);
		
		if(buildingFinded == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Building with id '" + idBuilding + "' no exist"))
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
>>>>>>> a38c21e19997bda637ce4e05c23be1d1b058a60d
	}
	

	@PUT
	@Path("/{idBuilding:[0-9]+}/floors")
	public Response createFloor(@PathParam("idBuilding") Long idBuilding, Floor floor) {
		Building building = buildingManager.findById(idBuilding );
		
		if(building == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Building with id '" + idBuilding + "' no exist"))
					.build();
		}
		
		if(floor.getFloorNumber() == 0) {
			return  Response
					.status(400)
					.entity(Utils.makeErrorMessage(400, "'FloorNumber' property is missing"))
					.build();
		}

		building.addFloor(floor);
		buildingManager.updateBuilding(building);
		
		building = buildingManager.findById(building.getId());
		
		Optional<Floor> floorAdded = building.getBuildingFloor()
				.stream()
				.filter(buildingFloor -> (buildingFloor.getFloorNumber()==(floor.getFloorNumber())))
				.findFirst();

		JsonObject jsonResponse = Json.createObjectBuilder().add("id", floorAdded.get().getId()).build();

		return Response.ok().entity(jsonResponse).build();
	}
	
	@DELETE
	@Path("/{idBuilding:[0-9]+}/floors/{id:[0-9]+}")
	public Response deleteFloor(@PathParam("idBuilding") Long idBuilding, @PathParam("id") Long idFloor) {
		Building building = buildingManager.findById(idBuilding );

		if(building == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "building id '" + idBuilding + "' no exist"))
					.build();
		}


		Optional<Floor> floorRemoved = building.getBuildingFloor()
				.stream().filter(c -> c.getId() == idFloor).findFirst();

		if(!floorRemoved.isPresent()) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "floor with id '" + idFloor + "' not found"))
					.build();
		}

		building.removeFloor(floorRemoved.get());
		buildingManager.updateBuilding(building);

		return Response.ok().build();
	}
}

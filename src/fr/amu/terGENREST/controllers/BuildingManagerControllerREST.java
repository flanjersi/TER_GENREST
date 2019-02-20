package fr.amu.terGENREST.controllers;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
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

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;

import fr.amu.terGENREST.controllers.utils.Utils;
import fr.amu.terGENREST.entities.environmentTechnical.Configuration;
import fr.amu.terGENREST.entities.environmentTechnical.Language;
import fr.amu.terGENREST.entities.project.Project;
import fr.amu.terGENREST.entities.projectSpecifications.Address;
import fr.amu.terGENREST.entities.projectSpecifications.Building;
import fr.amu.terGENREST.entities.projectSpecifications.Floor;
import fr.amu.terGENREST.entities.user.User;
import fr.amu.terGENREST.services.project.ProjectManager;
import fr.amu.terGENREST.services.projectSpecifications.BuildingManager;
import fr.amu.terGENREST.services.projectSpecifications.FloorManager;
import fr.amu.terGENREST.services.user.UserManager;

@Path("api/projects/{idProject:[0-9]+}/buildings")
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
	}

	@PUT
	@Path("{idBuilding:[0-9]+}/floors")
	public Response createFloor(@PathParam("idBuilding") Long idBuilding, Floor floor) {
		Building building = buildingManager.findById(idBuilding );
		if(building == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "Building with id '" + idBuilding + "' no exist"))
					.build();
		}
		
		
		//Delete the setup of the id by the user
				//floor.setId(0);

				building.addFloor(floor);
				buildingManager.updateBuilding(building);

				JsonObject jsonResponse = Json.createObjectBuilder().add("id", (JsonValue) floorManager.findById(floor.getId())).build();

				return Response.ok().entity(jsonResponse).build();
			}
	@DELETE
	@Path("{idBuilding:[0-9]+}/floors/{id:[0-9]+}")
	public Response deleteConfiguration(@PathParam("idBuilding") Long idBuilding, @PathParam("id") Long id) {
		Building building = buildingManager.findById(idBuilding );

		if(building == null) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "building id '" + idBuilding + "' no exist"))
					.build();
		}
		

		Optional<Floor> floor = building.getBuildingFloor()
				.stream().filter(c -> c.getId() == id).findFirst();
		
		if(!floor.isPresent()) {
			return Response
					.status(404)
					.entity(Utils.makeErrorMessage(404, "floor with id '" + id + "' not found"))
					.build();
		}
		
		building.removeFloor(floor.get());
		buildingManager.updateBuilding(building);
		
		return Response.ok().build();
	}
}
//		Long id = 1L;
//	
//		if(buildingManager.findById(id) == null) {
//			 User user = new User("firstName", "lastName", "email007@email.com", "password");
//			 userManager.saveUser(user);
//			Project project= new Project("firstProject");
//			user.addProject(project);
//			Address address = new Address("147 rue Aubagne","Marseille","France");
//			Building building = new Building("bat", address);
//			project.addBuilding(building);
//			id = project.getId();
//		}
//		
//		Building building = buildingManager.findById(id);
//		
//		building.addFloor(floor);
//		buildingManager.updateBuilding(building);
//		building = buildingManager.findById(building.getId());
//		Optional<Floor> floorAdded = building.getBuildingFloor()
//				.stream()
//				.filter(projectbuilding -> (projectbuilding.getFloorNumber.equals(floor.getFloorNumber())))
//				.findFirst();
//		
//		JsonObject jsonResponse = Json.createObjectBuilder().add("id", buildingAdded.get().getId()).build();
//		return Response.ok().entity(jsonResponse).build();
//		}

//			
//		@DELETE
//		@Path("/{id:[0-9]+}")
//		public Response deleteBuilding(@PathParam("id") Long id) {
//			
//			if(buildingManager.findById(id) == null) {
//				return Response
//						.status(404)
//						.entity(Utils.makeErrorMessage(404, "Building with id '" + id + "' no exist"))
//						.build();
//			}
//			
//			Project project = projectManager.findProject(id);
//			project.removeBuildings(buildingManager.findById(id));
//			projectManager.updateProject(project);
//			
//			return Response.ok().build();
//		}
//}
	
	
	
//		

//@PUT
//@Path("")
//public Response createBuilding(@PathParam("idProject") Long idProject, Building building) {
//	
//	Long id = 1L;
//
//	if(projectManager.findProject(id) == null) {
//		 User user = new User("firstName", "lastName", "email007@email.com", "password");
//		 userManager.saveUser(user);
//		Project project= new Project("firstProject");
//		
//		user.addProject(project);
//		id = project.getId();
//	}
//	
//	Project project = projectManager.findProject(id);
//	
//	if(building.getType() == null) {
//		return Response
//				.status(400)
//				.entity(Utils.makeErrorMessage(400, "'type' property is missing"))
//				.build();		
//	}
//	
//	project.addBuilding(building);
//	projectManager.updateProject(project);
//	
//	project = projectManager.findProject(project.getId());
//	Optional<Building> buildingAdded = project.getBuilding()
//			.stream()
//			.filter(projectbuilding -> (projectbuilding.getType().equals(building.getType())))
//			.findFirst();
//	
//	JsonObject jsonResponse = Json.createObjectBuilder().add("id", buildingAdded.get().getId()).build();
//	return Response.ok().entity(jsonResponse).build();
//	}


//	@DELETE
//	@Path("/{id:[0-9]+}")
//	public Response deleteBuilding(@PathParam("id") Long id) {
//		
//		if(buildingManager.findById(id) == null) {
//			return Response
//					.status(404)
//					.entity(Utils.makeErrorMessage(404, "Building with id '" + id + "' no exist"))
//					.build();
//		}
//		
//		Project project = projectManager.findProject(id);
//		project.removeBuildings(buildingManager.findById(id));
//		projectManager.updateProject(project);
//		
//		return Response.ok().build();
//	}
//		}
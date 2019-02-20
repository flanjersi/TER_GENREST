package fr.amu.terGENREST.controllers;


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
import fr.amu.terGENREST.controllers.utils.Utils;
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

	//TODO Création d'une méthode de mise à jour de l'étage
	
	//TODO Création d'une méthode d'ajout de corridor
	//TODO Création d'une méthode d'ajout de motherRoom

	//TODO Création d'une méthode de suppression de corridor
	//TODO Création d'une méthode de suppression de motherRoom

}

//TODO Trier les commentaires

//@PUT
//@Path("")
//public Response createFloor(@PathParam("idFloor") Long idFloor, Floor floor) {
//	
//	Long id = 1L;
//
//	if(buildingManager.findById(id) == null) {
//		 User user = new User("firstName", "lastName", "email007@email.com", "password");
//		 userManager.saveUser(user);
//		Project project= new Project("firstProject");
//		user.addProject(project);
//		Address address = new Address("147 rue Aubagne","Marseille","France");
//		Building building = new Building("bat", address);
//		project.addBuilding(building);
//		id = project.getId();
//	}
//	
//	Building building = buildingManager.findById(id);
//	
//	building.addFloor(floor);
//	buildingManager.updateBuilding(building);
//	building = buildingManager.findById(building.getId());
//	Optional<Floor> floorAdded = building.getBuildingFloor()
//			.stream()
//			.filter(projectbuilding -> (projectbuilding.getFloorNumber.equals(floor.getFloorNumber())))
//			.findFirst();
//	
//	JsonObject jsonResponse = Json.createObjectBuilder().add("id", buildingAdded.get().getId()).build();
//	return Response.ok().entity(jsonResponse).build();
//	}

//		
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

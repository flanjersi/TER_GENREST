package fr.amu.ter_genrest.controllers;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.amu.ter_genrest.entities.environment_technical.Configuration;
import fr.amu.ter_genrest.entities.environment_technical.Language;
import fr.amu.ter_genrest.entities.environment_technical.OperatingSystem;
import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.entities.project_specifications.Address;
import fr.amu.ter_genrest.entities.project_specifications.Building;
import fr.amu.ter_genrest.entities.project_specifications.Corridor;
import fr.amu.ter_genrest.entities.project_specifications.Floor;
import fr.amu.ter_genrest.entities.project_specifications.MotherRoom;
import fr.amu.ter_genrest.entities.project_specifications.Room;
import fr.amu.ter_genrest.entities.user.User;
import fr.amu.ter_genrest.services.generation.WebServiceGenerator;
import fr.amu.ter_genrest.services.project_specifications.ActuatorManager;
import fr.amu.ter_genrest.services.project_specifications.CorridorManager;
import fr.amu.ter_genrest.services.project_specifications.RoomManager;
import fr.amu.ter_genrest.services.user.UserManager;

@Path("api/deploiement")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WebServiceGeneratorREST {

	@EJB
	private ActuatorManager actuatorManager;

	@EJB
	private UserManager userManager;

	@EJB
	private RoomManager roomManager;

	@EJB
	private CorridorManager corridorManager;

	@EJB
	private WebServiceGenerator webServiceGenerator;
	
	private User user;
	private Project project;
	private Floor floor;
	private Building building;
	private MotherRoom motherRoom;
	private Corridor corridor;
	private Room room;
	
	private Language language = new Language();
	private Configuration configuration = new Configuration();
	private OperatingSystem operatingSystem;
	
	@PUT
	//@Path("/users/{idUser:[0-9]+}/projects/{idProject:[0-9]+}")  
	@Path("") // Le path n'est va être changé
	public Response generateAPI() { //@PathParam("idUser") Long idUser, @PathParam("idProject") Long idProject) {
		createEntities();
		webServiceGenerator.init();
		webServiceGenerator.buildDataRoutes(user, language, configuration, operatingSystem);
		webServiceGenerator.writeRoutesFile();
		return Response.ok().build();
	}
	
	public void createEntities() {
		 user = new User("firstName", "lastName", "email0@email.com", "password");
		 project = new Project("firstProject");
		user.addProject(project);
		Address address = new Address("147 rue Aubagne", "Marseille", "France");
		building = new Building("Batiment", address);
		project.addBuilding(building);
		floor = new Floor(1);
		building.addFloor(floor);
		 motherRoom = new MotherRoom("Appartement", 1);
		floor.addMotherRoom(motherRoom);
		room = new Room(1, "bed room");
		corridor = new Corridor(1);
		motherRoom.addRoom(room);
		motherRoom.addCorridor(corridor);
		userManager.saveUser(user);
	}
}

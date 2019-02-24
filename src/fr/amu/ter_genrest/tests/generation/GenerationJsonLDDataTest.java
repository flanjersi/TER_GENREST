package fr.amu.ter_genrest.tests.generation;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.json.Json;
import javax.json.JsonObject;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.entities.project_specifications.Actuator;
import fr.amu.ter_genrest.entities.project_specifications.Address;
import fr.amu.ter_genrest.entities.project_specifications.Building;
import fr.amu.ter_genrest.entities.project_specifications.Corridor;
import fr.amu.ter_genrest.entities.project_specifications.Floor;
import fr.amu.ter_genrest.entities.project_specifications.MotherRoom;
import fr.amu.ter_genrest.entities.project_specifications.Room;
import fr.amu.ter_genrest.entities.project_specifications.Sensor;
import fr.amu.ter_genrest.entities.user.User;
import fr.amu.ter_genrest.services.generation.GenerationJsonLD;
import fr.amu.ter_genrest.services.project_specifications.CorridorManager;
import fr.amu.ter_genrest.services.project_specifications.RoomManager;
import fr.amu.ter_genrest.services.project_specifications.SensorManager;
import fr.amu.ter_genrest.services.user.UserManager;

@Transactional
public class GenerationJsonLDDataTest {

	@EJB
	private SensorManager sensorManager;

	@EJB
	private UserManager userManager;

	@EJB
	private RoomManager roomManager;

	@EJB
	private CorridorManager corridorManager;

	@EJB
	private GenerationJsonLD generationJsonLD;

	
	private Project project;

	private User user;
	
	@Before
	public void setUp() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);

		user = new User("firstName", "lastName", "email0@email.com", "password");

		project = new Project("firstProject");
		user.addProject(project);

		Address address = new Address("147 rue Aubagne","Marseille","France");

		Building building = new Building("Batiment",address);
		project.addBuilding(building);

		Floor floor = new Floor(1);
		building.addFloor(floor);

		MotherRoom motherRoom = new MotherRoom("Appartement", 1);
		floor.addMotherRoom(motherRoom);

		Corridor corridorFloor = new Corridor(1);
		corridorFloor.addSensor(new Sensor(2, 2, "DHT22", "CHINA", "XXX", "UNKNOW", "Celsius"));
		corridorFloor.addActuator(new Actuator(2, 2, "SWITCH", "CHINA", "XXX", "UNKNOW"));
		
		
		floor.addCorridor(corridorFloor);
		
		Room room = new Room(1, "bed room");
		room.addSensor(new Sensor(2, 2, "DHT22", "CHINA", "XXX", "UNKNOW", "Celsius"));
		room.addActuator(new Actuator(2, 2, "SWITCH", "CHINA", "XXX", "UNKNOW"));
		
		Corridor corridor = new Corridor(1);
		corridor.addSensor(new Sensor(2, 2, "DHT22", "CHINA", "XXX", "UNKNOW", "Celsius"));
		corridor.addActuator(new Actuator(2, 2, "SWITCH", "CHINA", "XXX", "UNKNOW"));
		
		motherRoom.addRoom(room);
		motherRoom.addCorridor(corridor);
	
		userManager.saveUser(user);
	}

	@After
	public void tearDown() throws Exception {
		userManager.removeUser(user);
		
		EJBContainer.createEJBContainer().close();
	}
	
	@Test
	public void printGeneration() {
		System.out.println(generationJsonLD.generateJsonLDDataFile(project));
	}
	
}

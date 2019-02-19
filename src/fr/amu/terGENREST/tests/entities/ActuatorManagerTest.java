package fr.amu.terGENREST.tests.entities;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.terGENREST.entities.project.Project;
import fr.amu.terGENREST.entities.projectSpecifications.Actuator;
import fr.amu.terGENREST.entities.projectSpecifications.Address;
import fr.amu.terGENREST.entities.projectSpecifications.Building;
import fr.amu.terGENREST.entities.projectSpecifications.Corridor;
import fr.amu.terGENREST.entities.projectSpecifications.Floor;
import fr.amu.terGENREST.entities.projectSpecifications.MotherRoom;
import fr.amu.terGENREST.entities.projectSpecifications.Room;
import fr.amu.terGENREST.entities.projectSpecifications.Sensor;
import fr.amu.terGENREST.entities.user.User;
import fr.amu.terGENREST.services.projectSpecifications.ActuatorManager;
import fr.amu.terGENREST.services.projectSpecifications.CorridorManager;
import fr.amu.terGENREST.services.projectSpecifications.RoomManager;
import fr.amu.terGENREST.services.projectSpecifications.SensorManager;
import fr.amu.terGENREST.services.user.UserManager;

@Transactional
public class ActuatorManagerTest {

	@EJB
	private ActuatorManager actuatorManager;

	@EJB
	private UserManager userManager;

	@EJB
	private RoomManager roomManager;

	@EJB
	private CorridorManager corridorManager;

	private User user;

	private Room room;
	private Corridor corridor;

	@Before
	public void setUp() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);

		user = new User("firstName", "lastName", "email0@email.com", "password");

		Project project = new Project("firstProject");
		user.addProject(project);


		Address address = new Address("147 rue Aubagne","Marseille","France");

		Building building = new Building("Batiment",address);
		project.addBuilding(building);

		Floor floor = new Floor(1);
		building.addFloor(floor);

		MotherRoom motherRoom = new MotherRoom("Appartement", 1);
		floor.addMotherRoom(motherRoom);

		room = new Room(1, "bed room");
		corridor = new Corridor(1);
		motherRoom.addRoom(room);

		userManager.saveUser(user);
	}

	@After
	public void tearDown() throws Exception {
		userManager.removeUser(user);
		
		
		EJBContainer.createEJBContainer().close();
	}

	@Test
	public void testCRUDRoom() {    	
		//Add
		Actuator actuator = new Actuator(1, 1, "model", "brand", "reference", "off");
		room.addActuator(actuator);

		room = roomManager.updateRoom(room);

		actuator = room.getActuators().stream().max((a1, a2) -> a1.getId().compareTo(a2.getId())).get();

		long id = actuator.getId();

		//Find
		Actuator actuatorAdded = actuatorManager.findActuator(id);

		assertTrue(actuatorAdded.equals(actuator));

		//Update
		actuatorAdded.setBrand("brandUpdate");

		actuatorManager.updateActuator(actuatorAdded);

		//Find
		Actuator actuatorUpdated = actuatorManager.findActuator(id);

		assertTrue(actuatorUpdated.equals(actuatorAdded));

		//Delete
		room = roomManager.findRoom(room.getId());

		id = actuatorUpdated.getId();

		room.removeActuator(actuatorUpdated);

		roomManager.updateRoom(room);

		//Find
		assertNull(actuatorManager.findActuator(id));	    
	}
	
	@Test
	public void testCRUDCorridor() {    	
		//Add
		Actuator actuator = new Actuator(1, 1, "model", "brand", "reference", "off");
		corridor.addActuator(actuator);

		corridor = corridorManager.updateCorridor(corridor);

		actuator = corridor.getActuators().stream().max((a1, a2) -> a1.getId().compareTo(a2.getId())).get();

		long id = actuator.getId();

		//Find
		Actuator actuatorAdded = actuatorManager.findActuator(id);

		assertTrue(actuatorAdded.equals(actuator));

		//Update
		actuatorAdded.setBrand("brandUpdate");

		actuatorManager.updateActuator(actuatorAdded);

		//Find
		Actuator actuatorUpdated = actuatorManager.findActuator(id);

		assertTrue(actuatorUpdated.equals(actuatorAdded));

		//Delete
		corridor = corridorManager.findCorridor(corridor.getId());

		id = actuatorUpdated.getId();

		corridor.removeActuator(actuatorUpdated);

		corridorManager.updateCorridor(corridor);

		//Find
		assertNull(actuatorManager.findActuator(id));	   
	}
	
}

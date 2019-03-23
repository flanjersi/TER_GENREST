package fr.amu.ter_genrest.tests.entities;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
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
import fr.amu.ter_genrest.entities.user.User;
import fr.amu.ter_genrest.services.project_specifications.ActuatorManager;
import fr.amu.ter_genrest.services.project_specifications.CorridorManager;
import fr.amu.ter_genrest.services.project_specifications.RoomManager;
import fr.amu.ter_genrest.services.user.UserManager;

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
		
		LocalDateTime creationDate = LocalDateTime.of(2018, Month.DECEMBER, 25, 13, 37, 0);
		LocalDateTime changeDate = LocalDateTime.of(2018, Month.DECEMBER, 26, 13, 37, 0);
		Project project = new Project("firstProject", "domotic", creationDate, changeDate);
		
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
		motherRoom.addCorridor(corridor);		
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

package fr.amu.ter_genrest.tests.entities;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.entities.project_specifications.Address;
import fr.amu.ter_genrest.entities.project_specifications.Building;
import fr.amu.ter_genrest.entities.project_specifications.Corridor;
import fr.amu.ter_genrest.entities.project_specifications.Floor;
import fr.amu.ter_genrest.entities.project_specifications.Zone;
import fr.amu.ter_genrest.entities.project_specifications.Room;
import fr.amu.ter_genrest.entities.project_specifications.Sensor;
import fr.amu.ter_genrest.entities.user.User;
import fr.amu.ter_genrest.services.project_specifications.CorridorManager;
import fr.amu.ter_genrest.services.project_specifications.RoomManager;
import fr.amu.ter_genrest.services.project_specifications.SensorManager;
import fr.amu.ter_genrest.services.user.UserManager;

@Transactional
public class SensorManagerTest {

	@EJB
	private SensorManager sensorManager;

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

		Zone motherRoom = new Zone("Appartement", "1");
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
		Sensor sensor = new Sensor("sensor", 1, 1, "model", "unitData", "temperature");
		room.addSensor(sensor);

		room = roomManager.updateRoom(room);

		sensor = room.getSensors().stream().max((s1, s2) -> s1.getId().compareTo(s2.getId())).get();

		long id = sensor.getId();

		//Find
		Sensor sensorAdded = sensorManager.findSensor(id);

		assertTrue(sensorAdded.equals(sensor));

		//Update
		sensorAdded.setModel("modelUpdate");

		sensorManager.updateSensor(sensorAdded);

		//Find
		Sensor sensorUpdated = sensorManager.findSensor(id);

		assertTrue(sensorUpdated.equals(sensorAdded));

		//Delete
		room = roomManager.findRoom(room.getId());

		id = sensorUpdated.getId();

		room.removeSensor(sensorUpdated);

		roomManager.updateRoom(room);

		//Find
		assertNull(sensorManager.findSensor(id));	    
	}
	
	@Test
	public void testCRUDCorridor() {    	
		//Add
		Sensor sensor = new Sensor("sensor", 1, 1, "model", "unitData", "temperature");
		corridor.addSensor(sensor);

		corridor = corridorManager.updateCorridor(corridor);

		sensor = corridor.getSensors().stream().max((s1, s2) -> s1.getId().compareTo(s2.getId())).get();

		long id = sensor.getId();

		//Find
		Sensor sensorAdded = sensorManager.findSensor(id);

		assertTrue(sensorAdded.equals(sensor));

		//Update
		sensorAdded.setModel("modelUpdate");

		sensorManager.updateSensor(sensorAdded);

		//Find
		Sensor sensorUpdated = sensorManager.findSensor(id);

		assertTrue(sensorUpdated.equals(sensorAdded));

		//Delete
		corridor = corridorManager.findCorridor(corridor.getId());

		id = sensorUpdated.getId();

		corridor.removeSensor(sensorUpdated);

		corridor = corridorManager.updateCorridor(corridor);

		//Find
		assertNull(sensorManager.findSensor(id));	    
	}
}

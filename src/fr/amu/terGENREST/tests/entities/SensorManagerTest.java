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
import fr.amu.terGENREST.entities.projectSpecifications.Address;
import fr.amu.terGENREST.entities.projectSpecifications.Building;
import fr.amu.terGENREST.entities.projectSpecifications.Corridor;
import fr.amu.terGENREST.entities.projectSpecifications.Floor;
import fr.amu.terGENREST.entities.projectSpecifications.MotherRoom;
import fr.amu.terGENREST.entities.projectSpecifications.Room;
import fr.amu.terGENREST.entities.projectSpecifications.Sensor;
import fr.amu.terGENREST.entities.user.User;
import fr.amu.terGENREST.services.projectSpecifications.CorridorManager;
import fr.amu.terGENREST.services.projectSpecifications.RoomManager;
import fr.amu.terGENREST.services.projectSpecifications.SensorManager;
import fr.amu.terGENREST.services.user.UserManager;

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
		Sensor sensor = new Sensor(1, 1, "model", "brand", "reference", "state", "unitData");
		room.addSensor(sensor);

		room = roomManager.updateRoom(room);

		sensor = room.getSensors().stream().max((s1, s2) -> s1.getId().compareTo(s2.getId())).get();

		long id = sensor.getId();

		//Find
		Sensor sensorAdded = sensorManager.findSensor(id);

		assertTrue(sensorAdded.equals(sensor));

		//Update
		sensorAdded.setBrand("brandUpdate");

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
		Sensor sensor = new Sensor(1, 1, "model", "brand", "reference", "state", "unitData");
		corridor.addSensor(sensor);

		corridor = corridorManager.updateCorridor(corridor);

		sensor = corridor.getSensors().stream().max((s1, s2) -> s1.getId().compareTo(s2.getId())).get();

		long id = sensor.getId();

		//Find
		Sensor sensorAdded = sensorManager.findSensor(id);

		assertTrue(sensorAdded.equals(sensor));

		//Update
		sensorAdded.setBrand("brandUpdate");

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

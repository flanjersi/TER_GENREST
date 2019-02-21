package fr.amu.terGENREST.tests.entities;

import static org.junit.Assert.assertEquals;

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
import fr.amu.terGENREST.entities.user.User;
import fr.amu.terGENREST.services.projectSpecifications.CorridorManager;
import fr.amu.terGENREST.services.projectSpecifications.MotherRoomManager;
import fr.amu.terGENREST.services.user.UserManager;

@Transactional
public class CorridorManagerTest {

	@EJB
	private MotherRoomManager motherRoomManager;

	@EJB
	private CorridorManager corridorManager;

	@EJB
	private UserManager userManager;

	private MotherRoom motherRoom;
	private Corridor corridor1, corridor2, corridor3;
	private Floor floor = new Floor(1);
	private User user;

	@Before
	public void setUp() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);
		user = new User("firstName", "lastName", "email0@email.com", "password");

		Project project = new Project("firstProject");
		user.addProject(project);

		Address address = new Address("147 rue Aubagne", "Marseille", "France");

		Building building = new Building("Hotel", address);
		project.addBuilding(building);
		building.addFloor(floor);

		motherRoom = new MotherRoom("Appartement", 1);
		floor.addMotherRoom(motherRoom);

		corridor1 = new Corridor(1);
		corridor2 = new Corridor(2);
		corridor3 = new Corridor(3);
		motherRoom.addCorridor(corridor1);
		motherRoom.addCorridor(corridor2);
		motherRoom.addCorridor(corridor3);

		userManager.saveUser(user);

	}

	@After
	public void tearDown() throws Exception {
		userManager.removeUser(user);
		EJBContainer.createEJBContainer().close();
	}

	@Test
	public void testFindRoom() {

		MotherRoom motherWaiting = motherRoomManager.updateMotherRoom(motherRoom);

		assertEquals(3, motherWaiting.getCorridors().size());

		Corridor corridorWaiting = corridorManager.findCorridor(motherWaiting.getCorridors().get(0).getId());
		assertEquals(1, corridorWaiting.getNumberCorridor());

		Corridor corridorWaiting1 = corridorManager.findCorridor(motherWaiting.getCorridors().get(1).getId());
		assertEquals(2, corridorWaiting1.getNumberCorridor());

		Corridor corridorWaiting2 = corridorManager.findCorridor(motherWaiting.getCorridors().get(2).getId());
		assertEquals(3, corridorWaiting2.getNumberCorridor());
	}

	@Test
	public void testUpdateRoom() {


		motherRoomManager.updateMotherRoom(motherRoom);
		corridor1.setNumberCorridor(4);
		corridorManager.updateCorridor(corridor1);
		MotherRoom motherWaiting2 = motherRoomManager.updateMotherRoom(motherRoom);

		assertEquals(4, motherWaiting2.getCorridors().get(0).getNumberCorridor());
		assertEquals(2, motherWaiting2.getCorridors().get(1).getNumberCorridor());

		corridor2.setNumberCorridor(8);
		corridorManager.updateCorridor(corridor2);
		MotherRoom motherWaiting3 = motherRoomManager.updateMotherRoom(motherRoom);

		assertEquals(4, motherWaiting3.getCorridors().get(0).getNumberCorridor());
		assertEquals(8, motherWaiting3.getCorridors().get(1).getNumberCorridor());
		assertEquals(3, motherWaiting3.getCorridors().get(2).getNumberCorridor());

	}
}

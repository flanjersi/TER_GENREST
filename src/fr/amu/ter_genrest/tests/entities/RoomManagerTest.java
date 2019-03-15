package fr.amu.ter_genrest.tests.entities;

import static org.junit.Assert.*;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.entities.project_specifications.Address;
import fr.amu.ter_genrest.entities.project_specifications.Building;
import fr.amu.ter_genrest.entities.project_specifications.Floor;
import fr.amu.ter_genrest.entities.project_specifications.MotherRoom;
import fr.amu.ter_genrest.entities.project_specifications.Room;
import fr.amu.ter_genrest.entities.user.User;
import fr.amu.ter_genrest.services.project_specifications.MotherRoomManager;
import fr.amu.ter_genrest.services.project_specifications.RoomManager;
import fr.amu.ter_genrest.services.user.UserManager;

@Transactional
public class RoomManagerTest {

	@EJB
	private MotherRoomManager motherRoomManager;

	@EJB
	private RoomManager roomManager;

	@EJB
	private UserManager userManager;

	private MotherRoom motherRoom;
	private Room room1, room2, room3;
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

		room1 = new Room(1, "Chambre");
		room2 = new Room(2, "Toilettes");
		room3 = new Room(3, "Douche");
		motherRoom.addRoom(room1);
		motherRoom.addRoom(room2);
		motherRoom.addRoom(room3);

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

		assertEquals(3, motherWaiting.getRooms().size());
	}

/*TODO	@Test
	public void testUpdateRoom() {

		motherRoomManager.updateMotherRoom(motherRoom);
		room1.setNumberRoom(4);
		room1.setType("Cuisine");
		roomManager.updateRoom(room1);
		MotherRoom motherWaiting2 = motherRoomManager.updateMotherRoom(motherRoom);

		assertEquals(4, motherWaiting2.getRooms().get(0).getNumberRoom());
		assertEquals("Douche", motherWaiting2.getRooms().get(2).getType());

		room2.setNumberRoom(7);
		roomManager.updateRoom(room2);

		MotherRoom motherWaiting3 = motherRoomManager.updateMotherRoom(motherRoom);

		assertEquals(7, motherWaiting3.getRooms().get(1).getNumberRoom());
		assertEquals("Douche", motherWaiting3.getRooms().get(2).getType());

		room2.setNumberRoom(11);
		room2.setType("Dortoir");
		roomManager.updateRoom(room2);

		MotherRoom motherWaiting4 = motherRoomManager.updateMotherRoom(motherRoom);

		assertEquals("Dortoir", motherWaiting4.getRooms().get(1).getType());
		assertEquals("Douche", motherWaiting3.getRooms().get(2).getType());
	}*/

}

package fr.amu.ter_genrest.tests.entities;

import static org.junit.Assert.assertEquals;

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
import fr.amu.ter_genrest.entities.user.User;
import fr.amu.ter_genrest.services.project_specifications.CorridorManager;
import fr.amu.ter_genrest.services.project_specifications.ZoneManager;
import fr.amu.ter_genrest.services.user.UserManager;

@Transactional
public class CorridorManagerTest {

	@EJB
	private ZoneManager motherRoomManager;

	@EJB
	private CorridorManager corridorManager;

	@EJB
	private UserManager userManager;

	private Zone motherRoom;
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

		motherRoom = new Zone("Appartement", "1");
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
		Zone motherWaiting = motherRoomManager.updateZone(motherRoom);

		assertEquals(3, motherWaiting.getCorridors().size());
	}

/*TODO	@Test
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
*/
}

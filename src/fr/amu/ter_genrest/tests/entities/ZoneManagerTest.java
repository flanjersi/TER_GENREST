package fr.amu.ter_genrest.tests.entities;

import static org.junit.Assert.assertEquals;
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
import fr.amu.ter_genrest.entities.project_specifications.Address;
import fr.amu.ter_genrest.entities.project_specifications.Building;
import fr.amu.ter_genrest.entities.project_specifications.Floor;
import fr.amu.ter_genrest.entities.project_specifications.Zone;
import fr.amu.ter_genrest.entities.user.User;
import fr.amu.ter_genrest.services.project.ProjectManager;
import fr.amu.ter_genrest.services.project_specifications.BuildingManager;
import fr.amu.ter_genrest.services.project_specifications.FloorManager;
import fr.amu.ter_genrest.services.project_specifications.ZoneManager;
import fr.amu.ter_genrest.services.user.UserManager;

@Transactional
public class ZoneManagerTest {

	@EJB
	private BuildingManager buildingManager;
	
	@EJB
	private FloorManager floorManager;
	
	@EJB
	private ZoneManager MotherRoomManager;
	
	@EJB
	private ProjectManager projectManager;
	
	@EJB
	private UserManager userManager;
	
	private User user;
	
	private Project project;
	
	private Building building;
	
	private Floor floor; 
	
	private Zone motherRoom;
	
	@Before
	public void setUp() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);

		user = new User("firstName", "lastName", "email0@email.com", "password");

		LocalDateTime creationDate = LocalDateTime.of(2018, Month.DECEMBER, 25, 13, 37, 0);
		LocalDateTime changeDate = LocalDateTime.of(2018, Month.DECEMBER, 26, 13, 37, 0);
		 project = new Project("firstProject", "domotic", creationDate, changeDate);
		
		user.addProject(project);

		Address address = new Address("147 rue Aubagne","Marseille","France");

		building = new Building("Batiment",address);
		
		project.addBuilding(building);
		
		floor = new Floor(6);
		
		building.addFloor(floor);
		
		motherRoom = new Zone("Appartment", "2");
		
		floor.addMotherRoom(motherRoom);
		
		userManager.saveUser(user);
	}

	@After
	public void tearDown() throws Exception {
		userManager.removeUser(user);
		EJBContainer.createEJBContainer().close();
	}

	@Test
	public void testCRUDMotherRoom() {
		
		//Add
		floorManager.updateFloor(floor);
		Floor floorUpdated = floorManager.findById(floor.getId());
		assertTrue(floorUpdated.getZones().size() == 1);	
		
		//Update
		motherRoom.setType("studio");
		buildingManager.updateBuilding(building);		
		Floor floorFinded = floorManager.findById(floor.getId());
		Zone motherRoomU = floorFinded.getZones().stream().findFirst().get();
		assertEquals("studio", motherRoomU.getType());
		

		//Delete
		floorFinded.removeMotherRoom(motherRoom);
		floorManager.updateFloor(floorFinded);
		assertEquals(0,floorFinded.getZones().size());

	}
}

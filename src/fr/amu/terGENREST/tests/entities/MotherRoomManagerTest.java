package fr.amu.terGENREST.tests.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.terGENREST.entities.project.Project;
import fr.amu.terGENREST.entities.projectSpecifications.Address;
import fr.amu.terGENREST.entities.projectSpecifications.Building;
import fr.amu.terGENREST.entities.projectSpecifications.Floor;
import fr.amu.terGENREST.entities.projectSpecifications.MotherRoom;
import fr.amu.terGENREST.entities.user.User;
import fr.amu.terGENREST.services.projectSpecifications.BuildingManager;
import fr.amu.terGENREST.services.projectSpecifications.FloorManager;
import fr.amu.terGENREST.services.projectSpecifications.MotherRooomManager;
import fr.amu.terGENREST.services.user.UserManager;
import fr.amu.terGENREST.services.project.ProjectManager;
import javax.transaction.Transactional;

@Transactional
public class MotherRoomManagerTest {

	@EJB
	private BuildingManager buildingManager;
	
	@EJB
	private FloorManager floorManager;
	
	@EJB
	private MotherRooomManager MotherRoomManager;
	
	@EJB
	private ProjectManager projectManager;
	
	@EJB
	private UserManager userManager;
	
	private User user;
	
	private Project project;
	
	private Building building;
	
	private Floor floor; 
	
	private MotherRoom motherRoom;
	
	@Before
	public void setUp() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);

		user = new User("firstName", "lastName", "email0@email.com", "password");

		project = new Project("firstProject");
		
		user.addProject(project);

		Address address = new Address("147 rue Aubagne","Marseille","France");

		building = new Building("Batiment",address);
		
		project.addBuilding(building);
		
		floor = new Floor(6);
		
		building.addFloor(floor);
		
		motherRoom = new MotherRoom("Appartment", 2);
		
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
		assertTrue(floorUpdated.getBuildingMotherRoom().size() == 1);	
		
		//Update
		motherRoom.setType("studio");
		floorManager.updateFloor(floorUpdated);		
		userManager.updateUser(user);
		assertEquals("studio", floorManager.findById(floor.getId()).getBuildingMotherRoom().get(0).getType());
		
		
		//Delete
//		floorUpdated.removeMotherRoom(motherRoom);
//		floorManager.updateFloor(floorUpdated);
//		assertEquals(0, floorManager.findById(floorUpdated.getId()).getBuildingMotherRoom().size());

	}
}
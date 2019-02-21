package fr.amu.terGENREST.tests.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
import fr.amu.terGENREST.entities.user.User;
import fr.amu.terGENREST.services.projectSpecifications.BuildingManager;
import fr.amu.terGENREST.services.projectSpecifications.FloorManager;
import fr.amu.terGENREST.services.user.UserManager;
import fr.amu.terGENREST.services.project.ProjectManager;
import javax.transaction.Transactional;

@Transactional
public class FloorManagerTest {

	@EJB
	private BuildingManager buildingManager;
	
	@EJB
	private FloorManager floorManager;
	
	@EJB
	private ProjectManager projectManager;
	
	@EJB
	private UserManager userManager;
	
	private User user;
	
	private Project project;
	
	private Building building;
	
	private Floor floor; 
	
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
		
		userManager.saveUser(user);
	}

	@After
	public void tearDown() throws Exception {
		userManager.removeUser(user);
		EJBContainer.createEJBContainer().close();
	}

	@Test
	public void testCRUDFloor() {
		//Add
		buildingManager.updateBuilding(building);
		Building buildUpdated = buildingManager.findById(building.getId());
		assertTrue(buildUpdated.getFloors().size() == 1);	
		
		//Update
		floor.setFloorNumber(17);
		buildingManager.updateBuilding(buildUpdated);
		userManager.updateUser(user);
		assertEquals(17, floorManager.findById(floor.getId()).getFloorNumber());
//		
//		//Delete
//		buildUpdated.removeFloor(floor);
//		buildingManager.updateBuilding(buildUpdated);
//		assertEquals(0, buildingManager.findById(buildUpdated.getId()).getBuildingFloor().size());

	}
}

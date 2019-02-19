package fr.amu.terGENREST.tests.entities;


import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Assert;
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

public class BuildingManagerTest {

	@EJB
	private BuildingManager buildingManager;
	
	@EJB
	private FloorManager floorManager;
	
	@EJB
	private ProjectManager projectManager;
	
	@EJB
	private UserManager userManager;

	@Before
	public void setUp() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);
	}

	@After
	public void tearDown() throws Exception {
		EJBContainer.createEJBContainer().close();
	}

	@Test
	public void testAddBuilding() {
	
		Address adress = new Address("Luminy", "Marseille", "France");
		
		User user = new User("ramzi", "taleb", "aa@hotmail.com", "toto");
		
		Building building = new Building("Batiment",adress);
		
		Project project = new Project("firstProject");

		user.addProject(project);
		userManager.saveUser(user);
		project.addBuilding(building);
		projectManager.updateProject(project);
		Project projectFinded = projectManager.findProject(project.getId());
		assertTrue(projectFinded.getBuilding().size() == 1);	
    	userManager.removeUser(userManager.findUser(user.getId()));
	}

//	@Test
//	public void testremoveBuilding() {
//		Address adress = new Address("Luminy", "Marseille", "France");
//		
//		User user = new User("ramzi", "taleb", "aa@hotmail.com", "toto");
//		
//		Building building = new Building("Batiment",adress);
//		
//		Project project = new Project("firstProject");
//
//		user.addProject(project);
//		userManager.saveUser(user);
//		project.addBuilding(building);
//		projectManager.updateProject(project);
//		project.removeBuildings(building);
//		projectManager.updateProject(project);
//		Assert.assertTrue(projectManager.findProject(project.getId()).getBuilding().size() == 0);
//		userManager.removeUser(userManager.findUser(user.getId()));
//	}
	
//    @Test
//    public void testUpdateBuilding() {
//    	Address adress = new Address("Luminy", "Marseille", "France");
//    		
//    	User user = new User("ramzi", "taleb", "aa@hotmail.com", "toto");
//    		
//    	Building building = new Building("Batiment",adress);
//    		
//    	Project project = new Project("firstProject");
//    		
//    	user.addProject(project);
//    	userManager.saveUser(user);
//    	project.addBuilding(building);
//    	projectManager.updateProject(project);
//    	building.setType("local");
//    	projectManager.updateProject(project);
//    	Project projectFinded = projectManager.findProject(project.getId());
//      assertTrue(projectFinded.getBuilding().get(0).getType().equals("local"));
//      userManager.removeUser(userManager.findUser(user.getId()));
//    	}

}
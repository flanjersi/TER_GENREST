package fr.amu.terGENREST.tests.entities;

<<<<<<< HEAD

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

=======
import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.terGENREST.services.project.ProjectManager;
import fr.amu.terGENREST.services.projectSpecifications.BuildingManager;
import fr.amu.terGENREST.services.projectSpecifications.FloorManager;

@Transactional
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
public class BuildingManagerTest {

	@EJB
	private BuildingManager buildingManager;
	
	@EJB
	private FloorManager floorManager;
	
	@EJB
	private ProjectManager projectManager;
<<<<<<< HEAD
	
	@EJB
	private UserManager userManager;
=======
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a

	@Before
	public void setUp() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);
	}

	@After
	public void tearDown() throws Exception {
		EJBContainer.createEJBContainer().close();
	}

	@Test
<<<<<<< HEAD
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

=======
	public void testCRUD() {
//		
//		Building building = new Building(43, "Luminy", "Marseille", 13009, "France");
//		
//		//testAddBuilding
//		buildingManager.addBuilding(building);
//
//		Building build = buildingManager.findById(building.getId());
//
//		assertTrue(building.getCity().equals(build.getCity()));
//		
//		//testUpdateBuilding
//		build.setCity("Paris");
//    	
//    	buildingManager.updateBuilding(build);
//    	
//    	build = buildingManager.findById(build.getId());
//    	
//    	assertTrue(build.getCity().equals("Paris"));
//
//		//testRemoveBuilding
//		long id = build.getId();
//
//		buildingManager.removeBuilding(build);
//
//		Building buildingRemove = buildingManager.findById(id);
//
//		assertNull(buildingRemove);
		
	}
	
//	@Test
//	public void testAddProject() {
//		
//		Project project = new Project("firstProject");
//		Building building = new Building(43, "Luminy", "Marseille", 13009, "France");
//		project.addBuilding(building);
//		Building buildingAdded = buildingManager.findById(building.getId()); 
//		Assert.assertEquals(buildingAdded, building);	
//		projectManager.removeProject(project);
//	}
	
	
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
}
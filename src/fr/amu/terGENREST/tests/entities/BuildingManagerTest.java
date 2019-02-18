package fr.amu.terGENREST.tests.entities;

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
public class BuildingManagerTest {

	@EJB
	private BuildingManager buildingManager;
	
	@EJB
	private FloorManager floorManager;
	
	@EJB
	private ProjectManager projectManager;

	@Before
	public void setUp() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);
	}

	@After
	public void tearDown() throws Exception {
		EJBContainer.createEJBContainer().close();
	}

	@Test
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
	
	
}
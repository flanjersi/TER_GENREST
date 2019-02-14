package fr.amu.terGENREST.tests.entities;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.terGENREST.entities.projectSpecifications.Building;
import fr.amu.terGENREST.entities.projectSpecifications.Floor;
import fr.amu.terGENREST.services.projectSpecifications.BuildingManager;

public class BuildingManagerTest {

	@EJB
	private BuildingManager buildingManager;

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
		ArrayList<Floor> BF = new ArrayList<Floor>();

		Building building = new Building(43, "Luminy", "Marseille", 13009, "France", BF);

		buildingManager.addBuilding(building);

		Building build = buildingManager.findById(building.getId());

		assertTrue(building.getCity().equals(build.getCity()));

		// Remove building
		long id = build.getId();

		buildingManager.removeBuilding(build);

		Building buildingRemove = buildingManager.findById(id);

		assertNull(buildingRemove);
	}

//	@Test
//	public void testUpdateBuilding(){
//		
//	}
//	
//	
//	@Test
//	public void testFindBuilding(){
//	}	
}

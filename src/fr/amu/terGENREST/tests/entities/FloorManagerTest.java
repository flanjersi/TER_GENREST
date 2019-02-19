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
import fr.amu.terGENREST.entities.projectSpecifications.MotherRoom;
import fr.amu.terGENREST.entities.projectSpecifications.Building;
import fr.amu.terGENREST.entities.projectSpecifications.Floor;
import fr.amu.terGENREST.services.projectSpecifications.MotherRooomManager;
import fr.amu.terGENREST.services.projectSpecifications.BuildingManager;
import fr.amu.terGENREST.services.projectSpecifications.FloorManager;

public class FloorManagerTest {

	@EJB
	private FloorManager floorManager;
	
	@EJB
	private BuildingManager buildingManager;
	
	@EJB
	private MotherRooomManager motherRommManager;
	
	
	@Before
	public void setUp() throws Exception {
		 EJBContainer.createEJBContainer().getContext().bind("inject", this);
	}
	
	@After
	public void tearDown() throws Exception {
    	EJBContainer.createEJBContainer().close();
	}

	@Test
	public void testCRUD(){
		
		Address adress = new Address("Luminy", "Marseille", "France");
		
		Project project = new Project("firstProject");
		
		Building building = new Building("Batiment",adress);
		
		Floor floor = new Floor(5);
		//addFloorTest
		building.addFloor(floor);
		project.addBuilding(building);
		Building myBuilding = buildingManager.findById(project.getId());
		buildingManager.updateBuilding(building);
		assertTrue(myBuilding.getBuildingFloor().size() == 1);
//		
//////		//UpdateFloorTest
////		floor.setFloorNumber(9);
////		
////		floorManager.updateFloor(floor);
////		
////		myBuilding = buildingManager.findById(building.getId());
////		
////		Floor floorUpdate = myBuilding.getBuildingFloor().get(0);
////		
////		assertEquals(floorUpdate.getFloorNumber(), floor.getFloorNumber());
////		
//////		//RemoveFloorTest
////		long id = floorUpdate.getId();
////
////		myBuilding.removeFloor(floorUpdate);
////		
////		buildingManager.updateBuilding(myBuilding);
////		
////		myBuilding = buildingManager.findById(building.getId());
////		
////		assertTrue(myBuilding.getBuildingFloor().size() == 0);
////		assertTrue(floorManager.findById(id) == null);
////
////		buildingManager.removeBuilding(myBuilding);	
	}
}
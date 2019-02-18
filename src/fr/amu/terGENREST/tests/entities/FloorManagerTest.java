package fr.amu.terGENREST.tests.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.terGENREST.entities.projectSpecifications.Apartment;
import fr.amu.terGENREST.entities.projectSpecifications.Building;
import fr.amu.terGENREST.entities.projectSpecifications.Floor;
import fr.amu.terGENREST.services.projectSpecifications.ApartmentManager;
import fr.amu.terGENREST.services.projectSpecifications.BuildingManager;
import fr.amu.terGENREST.services.projectSpecifications.FloorManager;

public class FloorManagerTest {

	@EJB
	private FloorManager floorManager;
	
	@EJB
	private BuildingManager buildingManager;
	
	@EJB
	private ApartmentManager apartmentManager;
	
	
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
		
//		Building building = new Building(87, "Rue Paradis", "Marseille", 13005, "France");
//		
//		Floor floor = new Floor(5);
//		
//		Apartment app = new Apartment(2);
//		
//				//addFloorTest
//		building.addFloor(floor);
//				
//		buildingManager.addBuilding(building);
//				
//		Building myBuilding = buildingManager.findById(building.getId());
//			
//		assertTrue(myBuilding.getBuildingFloor().size() == 1);
//		
////		//UpdateFloorTest
//		floor.setFloorNumber(9);
//		
//		floorManager.updateFloor(floor);
//		
//		myBuilding = buildingManager.findById(building.getId());
//		
//		Floor floorUpdate = myBuilding.getBuildingFloor().get(0);
//		
//		assertEquals(floorUpdate.getFloorNumber(), floor.getFloorNumber());
//		
//		//RemoveFloorTest
//		long id = floorUpdate.getId();
//
//		myBuilding.removeFloor(floorUpdate);
//		
//		buildingManager.updateBuilding(myBuilding);
//		
//		myBuilding = buildingManager.findById(building.getId());
//		
//		assertTrue(myBuilding.getBuildingFloor().size() == 0);
//		assertTrue(floorManager.findById(id) == null);
//
//		buildingManager.removeBuilding(myBuilding);	
	}
}
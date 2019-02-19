//package fr.amu.terGENREST.tests.entities;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//
//import javax.ejb.EJB;
//import javax.ejb.embeddable.EJBContainer;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import fr.amu.terGENREST.entities.projectSpecifications.Address;
//import fr.amu.terGENREST.entities.projectSpecifications.MotherRoom;
//import fr.amu.terGENREST.entities.projectSpecifications.Building;
//import fr.amu.terGENREST.entities.projectSpecifications.Floor;
//import fr.amu.terGENREST.services.projectSpecifications.ApartmentManager;
//import fr.amu.terGENREST.services.projectSpecifications.BuildingManager;
//import fr.amu.terGENREST.services.projectSpecifications.FloorManager;
//
//public class ApartmentManagerTest {
//	
//
//	@EJB
//	private FloorManager floorManager;
//	
//	@EJB
//	private BuildingManager buildingManager;
//	
//	@EJB
//	private ApartmentManager apartmentManager;
//	
//	@Before
//	public void setUp() throws Exception {
//		 EJBContainer.createEJBContainer().getContext().bind("inject", this);
//	}
//	
//	@After
//	public void tearDown() throws Exception {
//    	EJBContainer.createEJBContainer().close();
//	}
//	
//	@Test
//	public void testCRUD(){
//		
//	Address adress = new Address("Luminy", "Marseille", "France");	
//	
//	Building building = new Building(adress);
//	
//	Floor floor = new Floor(4);
//		
//	MotherRoom app = new MotherRoom(2);
//
////	AddApptTest
//	floor.addAppartment(app);
//	building.addFloor(floor);
//	Floor floor2 = floorManager.findById(floor.getId());	
//	//Building myBuilding2 = buildingManager.findById(building.getId());
//	System.out.println("aaaaaaaaa"+floor2.getfloorAprtment().size());
//	assertNull(floor2.getfloorAprtment().size());
////	
////	//UpdateApartmentTest
////	
////	app.setApartmentNumber(18);
////	
////	apartmentManager.updateApartment(app);
////	
////	Floor floor2 = floorManager.findById(floor.getId());
////
////	Apartment appUpdate = floor.getfloorAprtment().get(0);
////	
////	assertEquals(appUpdate.getApartmentNumber(), app.getApartmentNumber());
////	
////
//////	//deleteAppTest
////	
////	Apartment appUpdate = myBuilding2.getFloorAprtment().get(0);
////	long id = appUpdate.getId();
////	myBuilding2.removeAppartment(appUpdate);
////	buildingManager.updateBuilding(myBuilding2);	
////	myBuilding2 = buildingManager.findById(building.getId());
////	assertTrue(myBuilding2.getBuildingFloor().size() == 0);
////	assertTrue(floorManager.findById(id) == null);
////	buildingManager.removeBuilding(myBuilding2);	
//}
//}

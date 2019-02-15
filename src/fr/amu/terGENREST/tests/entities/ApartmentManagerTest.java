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

import fr.amu.terGENREST.services.projectSpecifications.ApartmentManager;
import fr.amu.terGENREST.services.projectSpecifications.BuildingManager;
import fr.amu.terGENREST.services.projectSpecifications.FloorManager;

public class ApartmentManagerTest {
	

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
		
	Building building = new Building(87, "Rue Paradis", "Marseille", 13005, "France");
		
	Apartment app = new Apartment(2);

	//AddApptTest
	building.addAppartment(app);
	
	buildingManager.addBuilding(building);	
	
	Building myBuilding2 = buildingManager.findById(building.getId());

	assertTrue(myBuilding2.getFloorAprtment().size() == 1);
	
	//UpdateApartmentTest
	
//	app.setApartmentNumber(18);
//	
//	apartmentManager.updateApartment(app);
	
//	myBuilding2 = buildingManager.findById(building.getId());

//	Apartment appUpdate = myBuilding2.getFloorAprtment().get(0);
	
//	assertEquals(appUpdate.getApartmentNumber(), app.getApartmentNumber());
	

//	//deleteAppTest
	
	Apartment appUpdate = myBuilding2.getFloorAprtment().get(0);
	long id = appUpdate.getId();
	myBuilding2.removeAppartment(appUpdate);
	buildingManager.updateBuilding(myBuilding2);	
	myBuilding2 = buildingManager.findById(building.getId());
	assertTrue(myBuilding2.getBuildingFloor().size() == 0);
	assertTrue(floorManager.findById(id) == null);
	buildingManager.removeBuilding(myBuilding2);	
}
}

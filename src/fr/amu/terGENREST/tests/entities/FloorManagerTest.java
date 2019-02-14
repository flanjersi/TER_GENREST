package fr.amu.terGENREST.tests.entities;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.terGENREST.services.projectSpecifications.FloorManager;

public class FloorManagerTest {

	@EJB
	private FloorManager floorManager;
	
	
	@Before
	public void setUp() throws Exception {
		 EJBContainer.createEJBContainer().getContext().bind("inject", this);
	}
	
	@After
	public void tearDown() throws Exception {
    	EJBContainer.createEJBContainer().close();
	}

	@Test
	public void testAddBuilding(){
	}
	
	@Test
	public void testUpdateBuilding(){
	}
	
	@Test
	public void testRemoveBuilding(){
	}
	
	@Test
	public void testFindBuilding(){
	}	
}
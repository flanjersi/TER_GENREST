package fr.amu.terGENREST.tests.entities;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.terGENREST.entities.environmentTechnical.Configuration;
import fr.amu.terGENREST.entities.environmentTechnical.Language;
import fr.amu.terGENREST.entities.environmentTechnical.OperatingSystem;
import fr.amu.terGENREST.services.environmentTechnical.OperatingSystemManager;

public class OperatingSystemManagerTest {
	
	@EJB
	private OperatingSystemManager operatingSystemManager;
	
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
    	
    	OperatingSystem operatingSystem = new OperatingSystem("Raspbian", "test");
    	
    	operatingSystemManager.addOperatingSystem(operatingSystem);
    	
    	OperatingSystem operatingSystemFinded = operatingSystemManager.findById(operatingSystem.getId());
    	
    	assertTrue(operatingSystem.getName().equals(operatingSystemFinded.getName()));
    
    	operatingSystemFinded.setName("Raspbian update");
    	
    	operatingSystemManager.updateOperatingSystem(operatingSystemFinded);
    	
    	operatingSystemFinded = operatingSystemManager.findById(operatingSystemFinded.getId());
    	
    	assertTrue(operatingSystemFinded.getName().equals("Raspbian update"));

    	long id = operatingSystemFinded.getId();
    	
    	operatingSystemManager.removeOperatingSystem(operatingSystemFinded);
    	
    	OperatingSystem operatingSystemRemoved = operatingSystemManager.findById(id);
    	
    	assertNull(operatingSystemRemoved);
    }
}

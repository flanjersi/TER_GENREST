package fr.amu.ter_genrest.tests.entities;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.amu.ter_genrest.entities.environment_technical.Configuration;
import fr.amu.ter_genrest.entities.environment_technical.Language;
import fr.amu.ter_genrest.entities.environment_technical.OperatingSystem;
import fr.amu.ter_genrest.services.environment_technical.ConfigurationManager;
import fr.amu.ter_genrest.services.environment_technical.LanguagesManager;
import fr.amu.ter_genrest.services.environment_technical.OperatingSystemManager;

@Transactional
public class OperatingSystemManagerTest {
	
	@EJB
	private OperatingSystemManager operatingSystemManager;
	
	@EJB
	private ConfigurationManager configurationManager;
	
	
	@EJB
	private LanguagesManager languageManager ;
	
	
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
    	Language language = new Language("JavaScript");

		Configuration configuration = new Configuration("nodejs-express", "A description", "/templates/JavaScript/nodejs-express/");

		OperatingSystem operatingSystem = new OperatingSystem("Raspbian", "test");
    	
		configuration.getOperatingsSystem().add(operatingSystem);
		
		language.addConfiguration(configuration);
		
		languageManager.addLanguage(language);
  
    	OperatingSystem operatingSystemFinded = operatingSystemManager.findById(operatingSystem.getId());
    	
    	assertTrue(operatingSystem.getName().equals(operatingSystemFinded.getName()));
    
    	operatingSystemFinded.setName("Raspbian update");
    	
    	operatingSystemManager.updateOperatingSystem(operatingSystemFinded);
    	
    	operatingSystemFinded = operatingSystemManager.findById(operatingSystemFinded.getId());
    	
    	assertTrue(operatingSystemFinded.getName().equals("Raspbian update"));

    	long id = operatingSystemFinded.getId();
    	    	
    	configuration = configurationManager.findById(configuration.getId());
    	
    	configuration.getOperatingsSystem().remove(operatingSystemFinded);

    	configurationManager.updateConfiguration(configuration);
    	
    	OperatingSystem operatingSystemRemoved = operatingSystemManager.findById(id);
    	
    	assertNull(operatingSystemRemoved);
    }

}

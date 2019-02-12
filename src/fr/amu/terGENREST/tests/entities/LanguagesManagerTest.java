package fr.amu.terGENREST.tests.entities;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.terGENREST.entities.environmentTechnical.Configuration;
import fr.amu.terGENREST.entities.environmentTechnical.Language;
import fr.amu.terGENREST.services.environmentTechnical.LanguagesManager;

public class LanguagesManagerTest {
	
	@EJB
	private LanguagesManager languageManager;
	
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
    	
    	languageManager.addLanguage(language);
    	
    	Language languageFinded = languageManager.findById(language.getId());
    	
    	assertTrue(language.getName().equals(languageFinded.getName()));
    
    	Configuration configuration = new Configuration("nodejs-express", "A description", "/templates/JavaScript/nodejs-express/");
    	
    	languageFinded.addConfiguration(configuration);
    	
    	languageManager.updateLanguage(languageFinded);
    	
    	languageFinded = languageManager.findById(languageFinded.getId());
    	
    	assertTrue(languageFinded.getConfigurationsAvailable().size() == 1);

    	long id = languageFinded.getId();
    	
    	languageManager.removeLanguage(languageFinded);
    	
    	Language languageRemoved = languageManager.findById(id);
    	
    	assertNull(languageRemoved);
    }
    
    @Test(expected = EJBException.class)
    public void testUniqueNameConfigurationConstraint() {
    	Language language = new Language("JavaScript");
    	Configuration configuration = new Configuration("nodejs-express", "A description", "/templates/JavaScript/nodejs-express/");
    	language.addConfiguration(configuration);
    	
    	languageManager.addLanguage(language);
    	
    	Language languageError = new Language("JavaScript");
    	Configuration configurationError = new Configuration("nodejs-express", "A description", "/templates/Java/javaee-jaxrs/");
    	languageError.addConfiguration(configurationError);
    	
    	languageManager.addLanguage(languageError);
    	
    	languageManager.removeLanguage(language);
    }
    
    @Test(expected = EJBException.class)
    public void testUniquePathConfigurationConstraint() {
    	Language language = new Language("JavaScript");
    	Configuration configuration = new Configuration("nodejs-express", "A description", "/templates/JavaScript/nodejs-express/");
    	language.addConfiguration(configuration);
    	
    	languageManager.addLanguage(language);
    	
    	Language languageError = new Language("JavaScript");
    	Configuration configurationError = new Configuration("nodejs-express", "A description", "/templates/JavaScript/nodejs-express-http/");
    	languageError.addConfiguration(configurationError);
    	
    	languageManager.addLanguage(languageError);
    	
    	languageManager.removeLanguage(language);
    }
}

package fr.amu.terGENREST.tests.entities;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Assert;
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
    
    	languageFinded.setName("JavaScript-update");
    	
    	languageManager.updateLanguage(languageFinded);
    	
    	languageFinded = languageManager.findById(languageFinded.getId());
    	
    	assertTrue(languageFinded.getName().equals("JavaScript-update"));

    	long id = languageFinded.getId();
    	
    	languageManager.removeLanguage(languageFinded);
    	
    	Language languageRemoved = languageManager.findById(id);
    	
    	assertNull(languageRemoved);
    }
    
    @Test
    public void testUniqueNameConstraint() {
    	Language language = new Language("JavaScript");

		languageManager.addLanguage(language);

		Language languageError = new Language("JavaScript");
		
		try {
			languageManager.addLanguage(languageError);
			
			Assert.fail("Should have throw EJBException");
		}
		catch (EJBException e) {
			assertTrue(true);
		}
		
		languageManager.removeLanguage(language);
    }
    
    @Test
    public void testFindByName() {
    	Language language = new Language("JavaScript");

		languageManager.addLanguage(language);

		assertNotNull(languageManager.findByName("JavaScript"));
		
		languageManager.removeLanguage(language);
		
		assertNull(languageManager.findByName("JavaScript"));
    }
    

}

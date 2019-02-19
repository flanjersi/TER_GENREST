package fr.amu.terGENREST.tests.entities;

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

import fr.amu.terGENREST.entities.environmentTechnical.Configuration;
import fr.amu.terGENREST.entities.environmentTechnical.Language;
import fr.amu.terGENREST.services.environmentTechnical.ConfigurationManager;
import fr.amu.terGENREST.services.environmentTechnical.LanguagesManager;


@Transactional
public class ConfigurationManagerTest {

	@EJB
	private LanguagesManager languageManager;

	@EJB
	private ConfigurationManager configurationManager;

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

		language.addConfiguration(configuration);

		languageManager.addLanguage(language);

		Language languageFinded = languageManager.findById(language.getId());

		assertTrue(languageFinded.getConfigurationsAvailable().size() == 1);

		configuration.setName("nodejs-express-update");

		configurationManager.updateConfiguration(configuration);

		languageFinded = languageManager.findById(language.getId());

		Configuration configurationUpdate = languageFinded.getConfigurationsAvailable().get(0);

		assertTrue(configurationUpdate.getName().equals("nodejs-express-update"));

		long id = configurationUpdate.getId();

		languageFinded.removeConfiguration(configuration);
		languageManager.updateLanguage(languageFinded);

		languageFinded = languageManager.findById(language.getId());

		assertTrue(languageFinded.getConfigurationsAvailable().size() == 0);
		assertTrue(configurationManager.findById(id) == null);

		languageManager.removeLanguage(languageFinded);	
	}


	@Test
	public void testUniqueNameConfigurationConstraint() {
		Language language = new Language("JavaScript");
		Configuration configuration = new Configuration("nodejs-express", "A description", "/templates/JavaScript/nodejs-express/");
		language.addConfiguration(configuration);

		languageManager.addLanguage(language);

		Language languageError = new Language("JavaScript");
		Configuration configurationError = new Configuration("nodejs-express", "A description", "/templates/Java/javaee-jaxrs/");
		languageError.addConfiguration(configurationError);

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
	public void testRemoveAllConfigurationByRemoveLanguage() {
		Language language = new Language("JavaScript");
		Configuration configuration = new Configuration("nodejs-express", "A description", "/templates/JavaScript/nodejs-express/");

		language.addConfiguration(configuration);

		languageManager.addLanguage(language);

		long idConfiguration = configuration.getId();

		languageManager.removeLanguage(language);

		assertTrue(configurationManager.findById(idConfiguration) == null);
	}

	@Test
	public void testUniquePathConfigurationConstraint() {
		Language language = new Language("JavaScript");
		Configuration configuration = new Configuration("nodejs-express", "A description", "/templates/JavaScript/nodejs-express/");
		language.addConfiguration(configuration);

		languageManager.addLanguage(language);

		Language languageError = new Language("JavaScript");
		Configuration configurationError = new Configuration("nodejs-express", "A description", "/templates/JavaScript/nodejs-express-http/");
		languageError.addConfiguration(configurationError);

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
	public void testfindByName() {
		Language language = new Language("JavaScript");

		Configuration configuration = new Configuration("nodejs-express", "A description", "/templates/JavaScript/nodejs-express/");

		language.addConfiguration(configuration);

		languageManager.addLanguage(language);


		Configuration configurationNodeJS = configurationManager.findByName("nodejs-express");

		assertNotNull(configurationNodeJS);

		Configuration configurationNull = configurationManager.findByName("nodejs-express-null");
		assertNull(configurationNull);

		languageManager.removeLanguage(language);	
	}


	@Test
	public void testfindByPathFolder() {
		Language language = new Language("JavaScript");

		Configuration configuration = new Configuration("nodejs-express", "A description", "/templates/JavaScript/nodejs-express/");

		language.addConfiguration(configuration);

		languageManager.addLanguage(language);


		Configuration configurationNodeJS = configurationManager.findByPathFolder("/templates/JavaScript/nodejs-express/");

		assertNotNull(configurationNodeJS);

		Configuration configurationNull = configurationManager.findByPathFolder("/templates/JavaScript/nodejs-express-null/");
		assertNull(configurationNull);

		languageManager.removeLanguage(language);	
	}

}

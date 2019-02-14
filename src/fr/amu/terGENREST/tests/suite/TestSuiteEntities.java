package fr.amu.terGENREST.tests.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import fr.amu.terGENREST.tests.entities.ConfigurationManagerTest;
import fr.amu.terGENREST.tests.entities.LanguagesManagerTest;
import fr.amu.terGENREST.tests.entities.OperatingSystemManagerTest;
import fr.amu.terGENREST.tests.entities.UserManagerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	LanguagesManagerTest.class, 
	ConfigurationManagerTest.class,
	OperatingSystemManagerTest.class,
	UserManagerTest.class
})
public class TestSuiteEntities {

}

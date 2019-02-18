package fr.amu.terGENREST.tests.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import fr.amu.terGENREST.controllers.LanguagesManagerControllerREST;
import fr.amu.terGENREST.tests.endpointsWS.ConfigurationManagerControllerRESTTest;
import fr.amu.terGENREST.tests.endpointsWS.LanguageRESTControllerTest;
import fr.amu.terGENREST.tests.endpointsWS.OperatingSystemRESTControllerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	LanguageRESTControllerTest.class,
	ConfigurationManagerControllerRESTTest.class,
	OperatingSystemRESTControllerTest.class	
})
public class TestSuiteEndPointRest {

}

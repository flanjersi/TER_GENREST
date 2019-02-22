package fr.amu.terGENREST.tests.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import fr.amu.terGENREST.tests.endpointsWS.ActuatorRESTControllerTEST;
import fr.amu.terGENREST.tests.endpointsWS.BuildingRESTControllerTest;
import fr.amu.terGENREST.tests.endpointsWS.ConfigurationManagerControllerRESTTest;
import fr.amu.terGENREST.tests.endpointsWS.CorridorRESTControllerTest;
import fr.amu.terGENREST.tests.endpointsWS.FloorManagerControllerRESTTest;
import fr.amu.terGENREST.tests.endpointsWS.LanguageRESTControllerTest;
import fr.amu.terGENREST.tests.endpointsWS.OperatingSystemRESTControllerTest;
import fr.amu.terGENREST.tests.endpointsWS.ProjectManagerControllerRESTTest;
import fr.amu.terGENREST.tests.endpointsWS.SensorRESTControllerTest;
import fr.amu.terGENREST.tests.endpointsWS.UserRESTControllerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	UserRESTControllerTest.class,
	ProjectManagerControllerRESTTest.class,
	BuildingRESTControllerTest.class,
	FloorManagerControllerRESTTest.class,
	CorridorRESTControllerTest.class,
	//MotherRoomTest
	//RoomTest
	SensorRESTControllerTest.class,
	ActuatorRESTControllerTEST.class,
	LanguageRESTControllerTest.class,
	ConfigurationManagerControllerRESTTest.class,
	OperatingSystemRESTControllerTest.class	
})
public class TestSuiteEndPointRest {

}

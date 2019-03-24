package fr.amu.ter_genrest.tests.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import fr.amu.ter_genrest.tests.endpoints_ws.ActuatorRESTControllerTEST;
import fr.amu.ter_genrest.tests.endpoints_ws.BuildingRESTControllerTest;
import fr.amu.ter_genrest.tests.endpoints_ws.ConfigurationManagerControllerRESTTest;
import fr.amu.ter_genrest.tests.endpoints_ws.CorridorRESTControllerTest;
import fr.amu.ter_genrest.tests.endpoints_ws.EndPointRootTest;
import fr.amu.ter_genrest.tests.endpoints_ws.FloorManagerControllerRESTTest;
import fr.amu.ter_genrest.tests.endpoints_ws.LanguageRESTControllerTest;
import fr.amu.ter_genrest.tests.endpoints_ws.ZoneControllerRESTtest;
import fr.amu.ter_genrest.tests.endpoints_ws.OperatingSystemRESTControllerTest;
import fr.amu.ter_genrest.tests.endpoints_ws.ProjectManagerControllerRESTTest;
import fr.amu.ter_genrest.tests.endpoints_ws.RoomRESTControllerTest;
import fr.amu.ter_genrest.tests.endpoints_ws.SensorRESTControllerTest;
import fr.amu.ter_genrest.tests.endpoints_ws.UserRESTControllerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	EndPointRootTest.class,
	UserRESTControllerTest.class,
	ProjectManagerControllerRESTTest.class,
	BuildingRESTControllerTest.class,
	FloorManagerControllerRESTTest.class,
	CorridorRESTControllerTest.class,
	ZoneControllerRESTtest.class,
	RoomRESTControllerTest.class,
	SensorRESTControllerTest.class,
	ActuatorRESTControllerTEST.class,
	LanguageRESTControllerTest.class,
	ConfigurationManagerControllerRESTTest.class,
	OperatingSystemRESTControllerTest.class	
})
public class TestSuiteEndPointRest {

}

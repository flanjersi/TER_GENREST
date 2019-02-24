package fr.amu.terGENREST.tests.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import fr.amu.terGENREST.tests.entities.ActuatorManagerTest;
import fr.amu.terGENREST.tests.entities.BuildingManagerTest;
import fr.amu.terGENREST.tests.entities.ConfigurationManagerTest;
import fr.amu.terGENREST.tests.entities.CorridorManagerTest;
import fr.amu.terGENREST.tests.entities.FloorManagerTest;
import fr.amu.terGENREST.tests.entities.LanguagesManagerTest;
import fr.amu.terGENREST.tests.entities.MotherRoomManagerTest;
import fr.amu.terGENREST.tests.entities.OperatingSystemManagerTest;
import fr.amu.terGENREST.tests.entities.ProjectManagerImplTest;
import fr.amu.terGENREST.tests.entities.RoomManagerTest;
import fr.amu.terGENREST.tests.entities.SensorManagerTest;
import fr.amu.terGENREST.tests.entities.UserManagerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	LanguagesManagerTest.class, 
	ConfigurationManagerTest.class,
	OperatingSystemManagerTest.class,
	UserManagerTest.class,
	ProjectManagerImplTest.class,
	BuildingManagerTest.class,
	FloorManagerTest.class,
	MotherRoomManagerTest.class,
	CorridorManagerTest.class,
	RoomManagerTest.class,
	SensorManagerTest.class,
	ActuatorManagerTest.class
})
public class TestSuiteEntities {

}

package fr.amu.ter_genrest.tests.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import fr.amu.ter_genrest.tests.entities.ActuatorManagerTest;
import fr.amu.ter_genrest.tests.entities.BuildingManagerTest;
import fr.amu.ter_genrest.tests.entities.ConfigurationManagerTest;
import fr.amu.ter_genrest.tests.entities.CorridorManagerTest;
import fr.amu.ter_genrest.tests.entities.FloorManagerTest;
import fr.amu.ter_genrest.tests.entities.LanguagesManagerTest;
import fr.amu.ter_genrest.tests.entities.ZoneManagerTest;
import fr.amu.ter_genrest.tests.entities.OperatingSystemManagerTest;
import fr.amu.ter_genrest.tests.entities.ProjectManagerImplTest;
import fr.amu.ter_genrest.tests.entities.RoomManagerTest;
import fr.amu.ter_genrest.tests.entities.SensorManagerTest;
import fr.amu.ter_genrest.tests.entities.UserManagerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	LanguagesManagerTest.class, 
	ConfigurationManagerTest.class,
	OperatingSystemManagerTest.class,
	UserManagerTest.class,
	ProjectManagerImplTest.class,
	BuildingManagerTest.class,
	FloorManagerTest.class,
	ZoneManagerTest.class,
	CorridorManagerTest.class,
	RoomManagerTest.class,
	SensorManagerTest.class,
	ActuatorManagerTest.class
})
public class TestSuiteEntities {

}

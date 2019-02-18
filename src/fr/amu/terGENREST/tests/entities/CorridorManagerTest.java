package fr.amu.terGENREST.tests.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.terGENREST.entities.project.Project;
import fr.amu.terGENREST.entities.projectSpecifications.Address;
import fr.amu.terGENREST.entities.projectSpecifications.Building;
import fr.amu.terGENREST.entities.projectSpecifications.Corridor;
import fr.amu.terGENREST.entities.projectSpecifications.Floor;
import fr.amu.terGENREST.entities.projectSpecifications.MotherRoom;
import fr.amu.terGENREST.entities.projectSpecifications.Room;
import fr.amu.terGENREST.entities.user.User;
import fr.amu.terGENREST.services.project.ProjectManager;
import fr.amu.terGENREST.services.projectSpecifications.CorridorManager;
import fr.amu.terGENREST.services.user.UserManager;

public class CorridorManagerTest {

	@EJB
	ProjectManager projectManager;

	@EJB
	UserManager userManager;
	
	@EJB
	CorridorManager corridorManager;

	User user;

	Project project;
	
	Building building;
	
	Floor floor;
	
	MotherRoom motherRoom;
	
	Room room;
	
	Address adresse;
	
	Corridor corridor;

	@Before
	public void setUp() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);
		user = new User("firstName", "lastName", "email0@email.com", "password");
		project = new Project("firstProject");
		adresse = new Address("147 rue Aubagne","Marseille","France");
		
		building = new Building(adresse);
		floor = new Floor(1);
		motherRoom = new MotherRoom("Appartement",1);
		corridor = new Corridor(1);
		
		building.setAddress(adresse);
		
		
		motherRoom.addCorridor(corridor);
		floor.addMotherRoom(motherRoom);
		
		building.addFloor(floor);
		
		project.addBuilding(building);
		
		
		user.addProject(project);
		userManager.saveUser(user);
	}

	@After
	public void tearDown() throws Exception {
		EJBContainer.createEJBContainer().close();
	}

	@Test
	public void testFindCorridor() {

		assertEquals(1,userManager.findUser(user.getId()).getProjects().size());
		assertTrue(userManager.findUser(user.getId()).getProjects().get(0).getBuilding().get(0) instanceof Building );
		assertTrue(userManager.findUser(user.getId()).getProjects().get(0).getBuilding().get(0).getBuildingFloor().get(0).getBuildingFloor().get(0) instanceof MotherRoom );
		assertTrue(userManager.findUser(user.getId()).getProjects().get(0).getBuilding().get(0).getBuildingFloor().get(0).getBuildingFloor().get(0).getListCorridor().get(0) instanceof Corridor );
		Corridor corridorWaiting = userManager.findUser(user.getId()).getProjects().get(0).getBuilding().get(0).getBuildingFloor().get(0).getBuildingFloor().get(0).getListCorridor().get(0);
		assertNotNull(corridorManager.findCorridor(corridorWaiting.getId()));
	
	}
	
	public void testUpdateCorridor() {
		
		//userManager.findUser(user.getId()).getProjects().get(0).getBuilding().get(0).getBuildingFloor().get(0).getBuildingFloor().get(0).getListCorridor().get(0).setNumberCorridor(4);
		//corridorManager.findCorridor(findUser(user.getId()).getProjects().get(0).getBuilding().get(0).getBuildingFloor().get(0));
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

package fr.amu.ter_genrest.tests.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.entities.project_specifications.Address;
import fr.amu.ter_genrest.entities.project_specifications.Building;
import fr.amu.ter_genrest.entities.project_specifications.Floor;
import fr.amu.ter_genrest.entities.user.User;
import fr.amu.ter_genrest.services.project.ProjectManager;
import fr.amu.ter_genrest.services.project_specifications.BuildingManager;
import fr.amu.ter_genrest.services.project_specifications.FloorManager;
import fr.amu.ter_genrest.services.user.UserManager;

@Transactional
public class BuildingManagerTest {

	@EJB
	private BuildingManager buildingManager;

	@EJB
	private FloorManager floorManager;

	@EJB
	private ProjectManager projectManager;

	@EJB
	private UserManager userManager;

	private User user;

	private Project project;

	private Building building;

	@Before
	public void setUp() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);

		user = new User("firstName", "lastName", "email0@email.com", "password");

		LocalDateTime creationDate = LocalDateTime.of(2018, Month.DECEMBER, 25, 13, 37, 0);
		LocalDateTime changeDate = LocalDateTime.of(2018, Month.DECEMBER, 26, 13, 37, 0);
		project = new Project("firstProject", "domotic", creationDate, changeDate);

		user.addProject(project);

		Address address = new Address("147 rue Aubagne","Marseille","France");

		building = new Building("Batiment",address);

		project.addBuilding(building);

		userManager.saveUser(user);
	}

	@After
	public void tearDown() throws Exception {
		userManager.removeUser(userManager.findUser(user.getId()));
		EJBContainer.createEJBContainer().close();
	}

	@Test
	public void testCRUDBuilding() {
		//Add
		projectManager.updateProject(project);
		Project projectFinded = projectManager.findProject(project.getId());
		assertTrue(projectFinded.getBuilding().size() == 1);	

		//Update
		building.setType("Local");
		projectManager.updateProject(projectFinded);
		userManager.updateUser(user);
		assertEquals("Local", buildingManager.findById(building.getId()).getType());

		//Delete
		projectFinded.removeBuildings(building);
		projectManager.updateProject(projectFinded);
		
		
		assertEquals(0,projectManager.findProject(project.getId()).getBuilding().size());
	}

	@Test
	public void testFindBuildingByFloorId() {
		Floor floor = new Floor(1);

		Building building = buildingManager.findById(this.building.getId());

		building.addFloor(floor);

		building = buildingManager.updateBuilding(building);

		Building buildingByFloorID = buildingManager.findBuildingByFloorId(building.getFloors().stream().findFirst().get().getId());

		assertNotNull(buildingByFloorID);
		assertEquals(building.getId(), buildingByFloorID.getId());

		buildingByFloorID = buildingManager.findBuildingByFloorId(100L);
		assertNull(buildingByFloorID);
	}
}

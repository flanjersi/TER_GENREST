package fr.amu.terGENREST.tests.project;
import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import fr.amu.terGENREST.services.project.ProjectManager;
import fr.amu.terGENREST.services.user.UserManager;
import fr.amu.terGENREST.entities.project.Project;
import fr.amu.terGENREST.entities.user.User;


@Transactional
public class ProjectManagerImplTest {

	@EJB
	ProjectManager projectManager;

	@EJB
	UserManager userManager;

	
	@Before
	public void setUp() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);
	}

	@After
	public void tearDown() throws Exception {
		EJBContainer.createEJBContainer().close();
	}

	@Test
	public void testFindProject() {
		Project project = new Project("firstProject");
		Project project2 = new Project("secondProject");
		projectManager.addProject(project);
		projectManager.addProject(project2);
	}

	@Test
	public void testAddProject() {
		User user = new User("firstName", "lastName", "email0@email.com", "password");
		Project project = new Project("firstProject");
		user.addProject(project);
		userManager.saveUser(user);
		Project projcetAdded = projectManager.findProject(project.getId()); 
		Assert.assertEquals(projcetAdded, project);	
		userManager.removeUser(user);
	}

	@Test
	public void testRemoveProject() {
		User user = new User("firstName", "lastName", "email@email.com", "password");
		Project project = new Project("firstProject");		
		user.addProject(project);
		userManager.saveUser(user);
		user.removeProject(project);
		userManager.updateUser(user);
		Project projcetAdded = projectManager.findProject(project.getId()); 
		Assert.assertNull(projcetAdded);
		userManager.removeUser(user);
	}

	@Test
	public void testUpdateProject() {	
		User user = new User("firstName", "lastName", "email1@email.com", "password");
		Project project = new Project("firstProject");
		user.addProject(project);
		userManager.saveUser(user);
		project.setProjectName("secondProject");
		userManager.updateUser(user);
		Assert.assertEquals("secondProject", projectManager.findProject(project.getId()).getProjectName());
		userManager.removeUser(user);
	}
	
	@Test
	public void testFindAllProject() {
		User user = new User("firstName", "lastName", "email2@email.com", "password");
		User user2 = new User("firstName", "lastName", "email22@email.com", "password");
		Project project = new Project("firstProject");
		Project project2 = new Project("firstProject");
		Project project3 = new Project("firstProject");
		user.addProject(project);
		user.addProject(project2);
		user2.addProject(project3);
		userManager.saveUser(user);
		userManager.saveUser(user2);
		Assert.assertEquals(3,projectManager.findAllProject().size());		
		userManager.removeUser(user);
		userManager.removeUser(user2);
	}


}
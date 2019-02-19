package fr.amu.terGENREST.tests.project;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.activemq.store.jdbc.adapter.PostgresqlJDBCAdapter;
import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import fr.amu.terGENREST.services.project.ProjectManager;
import fr.amu.terGENREST.services.user.UserManager;
import fr.amu.terGENREST.entities.project.Project;
import fr.amu.terGENREST.entities.user.User;


/**
 * DAO test to project
 * @author Youcef
 *
 */


@Transactional
public class ProjectManagerImplTest {

	@EJB
	ProjectManager projectManager;

	@EJB
	UserManager userManager;

	User user;
	
	Project project;

	@Before
	public void setUp() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);
		user = new User("firstName", "lastName", "email0@email.com", "password");
		project = new Project("firstProject");
		user.addProject(project);
		userManager.saveUser(user);
	}

	@After
	public void tearDown() throws Exception {
		userManager.removeUser(user);
		EJBContainer.createEJBContainer().close();	
	}

	@Test
	public void testAddProject() {
		Project projcetAdded = projectManager.findProject(project.getId()); 
		Assert.assertEquals(projcetAdded, project);	
	}

	@Test
	public void testRemoveProject() {
		user.removeProject(project);
		userManager.updateUser(user);
		Assert.assertEquals(0,userManager.findUser(user.getId()).getProjects().size());
	}

	@Test
	public void testUpdateProject() {	
		project.setProjectName("secondProject");
		userManager.updateUser(user);
		Assert.assertEquals("secondProject", projectManager.findProject(project.getId()).getProjectName());
	}
	
	@Test
	public void testFindPojectByProjectName() {	
		List<Project> projects = projectManager.findByProjectName(project.getProjectName());
		long nbProject = projects.stream().filter(projectFinded -> projectFinded.equals(project)).count();
		Assert.assertTrue(nbProject != 0);
		
	}


}
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

	
	@Before
	public void setUp() throws Exception {
		EJBContainer.createEJBContainer().getContext().bind("inject", this);
	}

	@After
	public void tearDown() throws Exception {
		
		EJBContainer.createEJBContainer().close();
	}
	@Ignore
	@Test
	public void testFindProject() {
		Project project = new Project("firstProject");
		Project project2 = new Project("secondProject");
		projectManager.addProject(project);
		projectManager.addProject(project2);
	}
	@Ignore
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
		
//		User p = userManager.findUser(user.getId());
//		
//		p.removeProject(project);
		
		user.removeProject(project);
		userManager.updateUser(user);
		
		//userManager.findUser(user.getId()).removeProject(project);
		
		//Hibernate.initialize(p.getProjects());
		
		Assert.assertEquals(0,userManager.findUser(user.getId()).getProjects().size());
		
		userManager.removeUser(user);
		
	}

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Ignore
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
	
	
	@Ignore
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

package fr.amu.terGENREST.tests.entities;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import fr.amu.terGENREST.services.user.UserManager;

@Transactional
public class UserManagerTest {

	@EJB @InjectMocks
	UserManager userManager;
	
	
	@Before
	public void setUp() throws Exception {
		 EJBContainer.createEJBContainer().getContext().bind("inject", this);
	}

	@After
	public void tearDown() throws Exception {
    	EJBContainer.createEJBContainer().close();
	}

	public void testEJB() {
		 assertNotNull(userManager);
	}
	
	@Test
	public void testSaveUser() {
		
	}

	@Test
	public void testUpdateUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testAuthentification() {
		fail("Not yet implemented");
	}

}

package fr.amu.terGENREST.tests.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.amu.terGENREST.entities.user.User;
import fr.amu.terGENREST.services.user.UserManager;

@Transactional
public class UserManagerTest {
	
	/**
	 * DAO test to
	 * 
	 * @author Mohamed
	 *
	 */

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

	public void testEJB() {
		assertNotNull(userManager);
	}

	@Test
	public void testSaveUser() {
		User user = new User("Jean", "Marc", "j@gmail.fr", "password1");
		userManager.saveUser(user);
		User findedUser = userManager.findUser(user.getId());
		assertEquals(user.getEmail(), findedUser.getEmail());
	}

	@Test
	public void testUpdateUser() {
		User user = new User("Celine", "Emile", "ce@gmail.fr", "passwo");
		userManager.saveUser(user);
		user.setEmail("marc@gmail.fr");
		userManager.updateUser(user);
		User findedUser = userManager.findUser(user.getId());
		assertEquals(user.getEmail(), findedUser.getEmail());
	}

	@Test
	public void testRemoveUser() {

		User user = new User("dupond", "dupond", "dupond@gmail.fr", "assesz");
		userManager.saveUser(user);
		userManager.removeUser(user);
		User findedUser = userManager.findUser(user.getId());
		assertNull(findedUser);

	}

	@Test
	public void testFindUser() {
		User user = new User("Jack", "Marez", "ma@gmail.fr", "pass2");
		userManager.saveUser(user);
		User findedUser = userManager.findUser(user.getId());
		assertEquals(user.getEmail(), findedUser.getEmail());
	}

	@Test
	public void testAuthentification() {
		User user1 = new User("Nicky", "larson", "nick@gmail.fr", "azerty");
		userManager.saveUser(user1);
		assertNotNull(userManager.authentification("nick@gmail.fr", "azerty"));
	}

}

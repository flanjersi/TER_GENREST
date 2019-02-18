package fr.amu.terGENREST.tests.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
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
		userManager.removeUser(user);
	}
	
	@Test
	public void testUpdateUser() {
		User user = new User("Celine", "Emile", "ce@gmail.fr", "passwo");
		userManager.saveUser(user);
		user.setFirstName("marcde");
		userManager.updateUser(user);
		User findedUser = userManager.findUser(user.getId());
		assertEquals("marcde", findedUser.getFirstName());
		userManager.removeUser(user);
	}
	
	
	@Test
	public void testRemoveUser() {

		User user = new User("dupond", "dupond", "dupond@gmail.fr", "assesz");
		userManager.saveUser(user);
		userManager.removeUser(user);
		User findedUser = userManager.findUser(user.getId());
		assertNull(findedUser);
		userManager.removeUser(user);
	}
	
	@Test
	public void testFindUser() {
		User user = new User("Jack", "Marez", "ma@gmail.fr", "pass2");
		userManager.saveUser(user);
		User findedUser = userManager.findUser(user.getId());
		assertEquals(user.getEmail(), findedUser.getEmail());
		userManager.removeUser(user);
	}
	
	@Test
	public void testAuthentification() {
		User user1 = new User("Nicky", "larson", "nick@gmail.fr", "azerty");
		userManager.saveUser(user1);
		assertNotNull(userManager.authentification("nick@gmail.fr", "azerty"));
		userManager.removeUser(user1);
	}
	
	@Test
	public void testFindUserByEmail() {
		User user1 = new User("Mofe", "ieje", "eaijd@gmail.fr", "eid");
		userManager.saveUser(user1);
		assertNotNull(userManager.findUserByEmail("eaijd@gmail.fr"));
		userManager.removeUser(user1);
	}
	
	@Test
	public void testFindAllUser() {
		User user1 = new User("Nicky", "larson", "nick@gmail.fr", "azerty");
		User user2 = new User("Jack", "Michel", "eojf@hotmail.fr", "ezoifu");
		User user3 = new User("Amin", "Moed", "friezk@gmail.fr", "ofhez");
		userManager.saveUser(user1);
		userManager.saveUser(user2);
		userManager.saveUser(user3);
		
		assertTrue( userManager.findAllUser().size() == 3 );
		
		userManager.removeUser(user1);
		userManager.removeUser(user2);
		userManager.removeUser(user3);
	}
	
}

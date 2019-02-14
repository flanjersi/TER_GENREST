package fr.amu.terGENREST.tests.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.transaction.Transactional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import fr.amu.terGENREST.entities.user.User;
import fr.amu.terGENREST.services.user.UserManager;

@Transactional
public class UserManagerTest {
	
	/**
	 * DAO test to
	 * @author Mohamed
	 *
	 */
	
	private static UserManager mockedUserManager;
	private static User user1;
	private static User user2;
	
	@EJB
	UserManager userManager;
	
	@BeforeClass
	 public static void setUpClass() throws Exception {
		 //Create mock object of UserManager
		mockedUserManager = Mockito.mock(UserManager.class);
		
	    //Create few instances of User class.
		user1 = new User("Jean","Marc","j@gmail.fr","password1");
		user2 = new User("Med","Moud","m@gmail.fr","password2");
		mockedUserManager.saveUser(user1);
		mockedUserManager.saveUser(user2);
	}
	
	@AfterClass
	public static void tearDownClass() throws Exception {
	    // Code executed after the last test method 
	}
	
	@Before
	public void setUp() throws Exception {
		 EJBContainer.createEJBContainer().getContext().bind("inject", this);
		 MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
    	EJBContainer.createEJBContainer().close();
	}

	public void testEJB() {
		 assertNotNull(mockedUserManager);
	}
	
	@Test
	public void testSaveUser() {
	    //Stubbing saveUser of mocked mockedUserManager with mocked data. 
		when(mockedUserManager.saveUser(user1)).thenReturn(user1.getId());
		Long id = mockedUserManager.saveUser(user1);
		assertNotNull(id);
		assertEquals(user1.getId(), id);
	}

	@Test
	public void testUpdateUser() {
		when(mockedUserManager.updateUser(user1)).thenReturn(user1.getId());
		Long id = mockedUserManager.updateUser(user1);
		assertNotNull(id);
		assertEquals(user1.getId(), id);
	}

	@Test
	public void testRemoveUser() {
		when(mockedUserManager.removeUser(user1)).thenReturn(user1.getId());
		Long id = mockedUserManager.removeUser(user1);
		assertNotNull(id);		
	}

	@Test
	public void testFindAllUser() {
		when(mockedUserManager.findAllUser()).thenReturn(Arrays.asList(user1,user2));
		List<User> allUsers = mockedUserManager.findAllUser();
		assertEquals(2, allUsers.size()); 	// Size registered user
	    assertEquals(new Long(1), user1.getId());
	    assertEquals("Jean", user1.getFirstName());
	    assertEquals("Marc", user1.getLastName());
	    assertEquals("j@gmail.fr", user1.getEmail());
	    assertEquals("password1", user1.getPassword());
	}

	@Test
	public void testFindUser() {
		when(mockedUserManager.findUser(new Long(1))).thenReturn(user1);
		Long id = new Long(1);
		User user = mockedUserManager.findUser(id);
		assertNotNull(user);
	    assertEquals(id, user.getId());
	    assertEquals("Jean", user.getFirstName());
	    assertEquals("Marc", user.getLastName());
	    assertEquals("j@gmail.fr", user.getEmail());
	    assertEquals("password1", user.getPassword());
	}

	@Test
	public void testAuthentification() {
		when(mockedUserManager.authentification("j@gmail.fr","password1")).thenReturn(user1);
		String email="j@gmail.fr"; 
		String password="password1";
		User user = mockedUserManager.authentification(email, password);
		assertNotNull(user);
	}

}

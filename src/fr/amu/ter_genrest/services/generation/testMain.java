package fr.amu.ter_genrest.services.generation;

import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.Transactional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.amu.ter_genrest.entities.environment_technical.Configuration;
import fr.amu.ter_genrest.entities.environment_technical.Language;
import fr.amu.ter_genrest.entities.environment_technical.OperatingSystem;
import fr.amu.ter_genrest.entities.project.Project;
import fr.amu.ter_genrest.entities.project_specifications.Address;
import fr.amu.ter_genrest.entities.project_specifications.Building;
import fr.amu.ter_genrest.entities.project_specifications.Corridor;
import fr.amu.ter_genrest.entities.project_specifications.Floor;
import fr.amu.ter_genrest.entities.project_specifications.MotherRoom;
import fr.amu.ter_genrest.entities.project_specifications.Room;
import fr.amu.ter_genrest.entities.user.User;
import fr.amu.ter_genrest.services.project_specifications.ActuatorManager;
import fr.amu.ter_genrest.services.project_specifications.CorridorManager;
import fr.amu.ter_genrest.services.project_specifications.RoomManager;
import fr.amu.ter_genrest.services.user.UserManager;

@Transactional
public class testMain {

	static EJBContainer ejbContainer;
    static Context context;
 
	private Language language = new Language();
	private Configuration configuration = new Configuration();
	private OperatingSystem operatingSystem;
    
    @BeforeClass
    public static void beforeTests() throws NamingException {
        System.out.println("-=-=-=-= INIT CONTAINER");
        ejbContainer = EJBContainer.createEJBContainer();
        context = ejbContainer.getContext();
    }

    @AfterClass
    public static void afterTests() throws NamingException {
        System.out.println("-=-=-=-= CLOSE CONTAINER");
        context.close();
        ejbContainer.close();
    }
    
    @Test
    public void testGenrerator() throws Exception {
    	Properties properties = new Properties();
    	properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
    	context = new InitialContext(properties);    	
    	Object ref = context.lookup("WebServiceGeneratorImplLocal");
        Assert.assertTrue(ref instanceof WebServiceGenerator);
        WebServiceGenerator webServiceGenerator = (WebServiceGenerator) ref; 
        
        User user = createEntities();
		webServiceGenerator.init();
		webServiceGenerator.buildDataRoutes(user, language, configuration, operatingSystem);
		webServiceGenerator.writeRoutesFile();
		
		// Delete User
		properties = new Properties();
        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
        context = new InitialContext(properties);    	
        ref = context.lookup("UserManagerImplLocal");
		
        ((UserManager) ref).removeUser(user);
    }
    
    public User createEntities() throws NamingException {
    	 Project project;
    	 Floor floor;
    	 Building building;
    	 MotherRoom motherRoom;
    	 Corridor corridor;
    	 Room room;
    	
    	Properties properties = new Properties();
        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
        context = new InitialContext(properties);    	
        Object ref = context.lookup("UserManagerImplLocal");
        	
		User user = new User("firstName", "lastName", "email0@email.com", "password");
		
		project = new Project("firstProject");
		user.addProject(project);
		Address address = new Address("147 rue Aubagne", "Marseille", "France");
		building = new Building("Batiment", address);
		project.addBuilding(building);
		floor = new Floor(1);
		building.addFloor(floor);
		 motherRoom = new MotherRoom("Appartement", 1);
		floor.addMotherRoom(motherRoom);
		room = new Room(1, "bed room");
		corridor = new Corridor(1);
		motherRoom.addRoom(room);
		motherRoom.addCorridor(corridor);
		((UserManager) ref).saveUser(user);
		return user;
	}
}

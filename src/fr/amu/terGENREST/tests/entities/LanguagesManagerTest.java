package fr.amu.terGENREST.tests.entities;

import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.amu.terGENREST.entities.environmentTechnical.Language;

public class LanguagesManagerTest {
	
	@Before
    public void setUp() throws Exception {
        EJBContainer.createEJBContainer().getContext().bind("inject", this);
	}

    @After
    public void tearDown() throws Exception {
    	EJBContainer.createEJBContainer().close();
    }
    
    @Test
    public void testCRUD() {
    	
    	Language language = new Language("JavaScript");
    	
    	/*
    	Person person = new Person("Jeremy", "Gros", new Date(System.currentTimeMillis()), "1234", "example@example.com");	
    	
    	personManager.addPerson(person);
    
    	Person personAdded = personManager.findPerson(person.getId());
    	
    	Assert.assertTrue(personAdded.equals(person));;
     
    	personAdded.setFirstName("Simon");
    	
    	personManager.updatePerson(personAdded);
    	
    	Person personUpdate = personManager.findPerson(personAdded.getId());
    	
    	Assert.assertTrue(personUpdate.equals(personAdded));
    	
    	long idRemoved = personUpdate.getId();
    	
    	personManager.removePerson(personUpdate);
    	
    	Person personDeleted = personManager.findPerson(idRemoved);
    	
    	Assert.assertNull(personDeleted);
    	*/
    }
    
}

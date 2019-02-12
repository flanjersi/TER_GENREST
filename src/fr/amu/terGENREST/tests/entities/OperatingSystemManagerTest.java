package fr.amu.terGENREST.tests.entities;

import javax.ejb.embeddable.EJBContainer;

import org.junit.After;
import org.junit.Before;

public class OperatingSystemManagerTest {
	
	@Before
    public void setUp() throws Exception {
        EJBContainer.createEJBContainer().getContext().bind("inject", this);
	}

    @After
    public void tearDown() throws Exception {
    	EJBContainer.createEJBContainer().close();
    }
}

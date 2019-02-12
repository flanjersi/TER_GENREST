package fr.amu.terGENREST.services.environmentTechnical;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.amu.terGENREST.entities.environmentTechnical.OperatingSystem;

@Stateless
public class OperatingSystemManagerImpl implements OperatingSystemManager{

	@PersistenceContext(unitName = "database")
    private EntityManager em;
	
	@Override
	public void addOperatingSystem(OperatingSystem operatingSystem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OperatingSystem updateOperatingSystem(OperatingSystem operatingSystem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeOperatingSystem(OperatingSystem operatingSystem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void findAllOperatingSystem() {
		// TODO Auto-generated method stub
		
	}

}

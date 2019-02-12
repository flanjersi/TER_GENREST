package fr.amu.terGENREST.services.environmentTechnical;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.amu.terGENREST.entities.environmentTechnical.Language;

@Stateless
public class LanguagesManagerImpl implements LanguagesManager{

	@PersistenceContext(unitName = "database")
    private EntityManager em;
	
	@Override
	public void addLanguage(Language language) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Language updateLanguage(Language language) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeLanguage(Language language) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void findAllLanguages() {
		// TODO Auto-generated method stub
		
	}

}

package fr.amu.terGENREST.services.environmentTechnical;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.amu.terGENREST.entities.environmentTechnical.Configuration;
import fr.amu.terGENREST.entities.environmentTechnical.Language;

@Stateless
public class ConfigurationManagerImpl implements ConfigurationManager{

	@PersistenceContext(unitName = "database")
    private EntityManager em;

	@Override
	public Configuration updateConfiguration(Configuration configuration) {
		return em.merge(configuration);
	}

	@Override
	public Configuration findById(long id) {
		return em.find(Configuration.class, id);
	}
}

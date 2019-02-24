package fr.amu.ter_genrest.services.environment_technical;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.amu.ter_genrest.entities.environment_technical.Configuration;
import fr.amu.ter_genrest.entities.environment_technical.Language;

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

	@Override
	public Configuration findByName(String name) {
		TypedQuery<Configuration> q = em.createNamedQuery("Configuration.findByName", Configuration.class).setParameter("name", name);
		try {
			return q.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public Configuration findByPathFolder(String pathFolder) {
		TypedQuery<Configuration> q = em.createNamedQuery("Configuration.findByPathFolder", Configuration.class).setParameter("pathFolder", pathFolder);
		try {
			return q.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
}

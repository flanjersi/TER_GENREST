package fr.amu.ter_genrest.services.environment_technical;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.amu.ter_genrest.entities.environment_technical.Language;
import fr.amu.ter_genrest.entities.environment_technical.OperatingSystem;

@Stateless
public class OperatingSystemManagerImpl implements OperatingSystemManager{

	@PersistenceContext(unitName = "database")
    private EntityManager em;
	


	@Override
	public OperatingSystem updateOperatingSystem(OperatingSystem operatingSystem) {
		return em.merge(operatingSystem);
	}

	@Override
	public OperatingSystem findById(long id) {
		return em.find(OperatingSystem.class, id);
	}

}

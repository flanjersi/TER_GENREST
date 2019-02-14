package fr.amu.terGENREST.services.environmentTechnical;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.amu.terGENREST.entities.environmentTechnical.Language;
import fr.amu.terGENREST.entities.environmentTechnical.OperatingSystem;

@Stateless
public class OperatingSystemManagerImpl implements OperatingSystemManager{

	@PersistenceContext(unitName = "database")
    private EntityManager em;
	
	@Override
	public void addOperatingSystem(OperatingSystem operatingSystem) {
		em.persist(operatingSystem);
	}

	@Override
	public OperatingSystem updateOperatingSystem(OperatingSystem operatingSystem) {
		return em.merge(operatingSystem);
	}

	@Override
	public void removeOperatingSystem(OperatingSystem operatingSystem) {
		em.remove(em.contains(operatingSystem) ? operatingSystem : em.merge(operatingSystem));
		
	}

	@Override
	public OperatingSystem findById(long id) {
		return em.find(OperatingSystem.class, id);
	}

	@Override
	public List<OperatingSystem> findAllOperatingSystem() {
		TypedQuery<OperatingSystem> q = em.createNamedQuery("OperatingSystem.findAllOperatingSystems", OperatingSystem.class);
		
		return q.getResultList();
	}

	@Override
	public OperatingSystem findByName(String name) {
		TypedQuery<OperatingSystem> q = em.createNamedQuery("OperatingSystem.findByName", OperatingSystem.class)
				.setParameter("name", name);
		
		try {
			return q.getSingleResult();	
		} catch (NoResultException e) {
			return null;
		}
	}

}

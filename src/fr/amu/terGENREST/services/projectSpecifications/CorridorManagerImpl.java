package fr.amu.terGENREST.services.projectSpecifications;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;


import fr.amu.terGENREST.entities.projectSpecifications.Corridor;
import fr.amu.terGENREST.services.DBException;

@Stateless
public class CorridorManagerImpl implements CorridorManager {

	@PersistenceContext(unitName = "database")
	private EntityManager em;

	@Override
	public void removeCorridor(Corridor corridor) {
		em.remove(em.contains(corridor) ? corridor : em.merge(corridor));
	}

	@Override
	public Corridor findCorridor(Long id) {
		return em.find(Corridor.class, id);
	}


	@Override
	public void addCorridor(Corridor corridor) {
       em.persist(corridor);		
	}

	@Override
	public Corridor updateCorridor(Corridor corridor) {
		return em.merge(corridor);
	}

	@Override  
	public List<Corridor> findAllCorridor() {
		try {
			return em.createNamedQuery("Corridor.findAllCorridor").getResultList();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

}

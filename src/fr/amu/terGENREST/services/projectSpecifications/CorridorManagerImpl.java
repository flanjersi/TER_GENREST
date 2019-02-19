package fr.amu.terGENREST.services.projectSpecifications;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import fr.amu.terGENREST.entities.projectSpecifications.Corridor;

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

}

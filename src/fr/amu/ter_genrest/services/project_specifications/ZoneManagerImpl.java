package fr.amu.ter_genrest.services.project_specifications;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.amu.ter_genrest.entities.project_specifications.Zone;

@Stateless
public class ZoneManagerImpl implements ZoneManager {

	@PersistenceContext(unitName = "database")
    private EntityManager em;
	
	@Override
	public Zone updateZone(Zone motherRoom) {
		return em.merge(motherRoom);
	}

	@Override
	public Zone findById(Long id) {
		return em.find(Zone.class, id);
	}

	@Override
	public List<Zone> findAllZone() {
		TypedQuery<Zone> q = em.createNamedQuery("Zone.findAllZone", Zone.class);
		return q.getResultList();
	}
}
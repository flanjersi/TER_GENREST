package fr.amu.terGENREST.services.projectSpecifications;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.amu.terGENREST.entities.projectSpecifications.Floor;

@Stateless
public class FloorManagerImpl implements FloorManager{

	@PersistenceContext(unitName = "database")
    private EntityManager em;
	

	@Override
	public Floor updateFloor(Floor floor) {
		return em.merge(floor);
	}

	@Override
	public Floor findById(Long id) {
		return em.find(Floor.class, id);
	}
	
	@Override
	public List<Floor> findAllFloor() {
		TypedQuery<Floor> q = em.createNamedQuery("floor.findAllFloor", Floor.class);
		return q.getResultList();
	}
}

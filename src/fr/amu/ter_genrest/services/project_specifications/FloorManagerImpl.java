package fr.amu.ter_genrest.services.project_specifications;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.amu.ter_genrest.entities.project_specifications.Floor;

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
package fr.amu.ter_genrest.services.project_specifications;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import fr.amu.ter_genrest.entities.project_specifications.Corridor;
import fr.amu.ter_genrest.services.exceptions.DBException;

@Stateless
public class CorridorManagerImpl implements CorridorManager {

	@PersistenceContext(unitName = "database")
	private EntityManager em;

	@Override
	public Corridor findCorridor(Long id) {
		return em.find(Corridor.class, id);
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
			return new ArrayList<>();
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

}

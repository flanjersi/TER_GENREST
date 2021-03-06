package fr.amu.ter_genrest.services.project_specifications;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.amu.ter_genrest.entities.project_specifications.Actuator;

@Stateless
public class ActuatorManagerImpl implements ActuatorManager {

	@PersistenceContext(unitName = "database")
	private EntityManager em;


	@Override
	public Actuator findActuator(Long id) {
		return em.find(Actuator.class, id);
	}

	@Override
	public Actuator updateActuator(Actuator actuator) {
		return em.merge(actuator);
	}

	@Override
	public List<Actuator> findAll() {
		TypedQuery<Actuator> q = em.createNamedQuery("Actuator.findAll",Actuator.class); 
		return q.getResultList();
	}

}

package fr.amu.terGENREST.services.projectSpecifications;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.amu.terGENREST.entities.projectSpecifications.Sensor;

@Stateless
public class SensorManagerImpl implements SensorManager {

	@PersistenceContext(unitName = "database")
	private EntityManager em;

	@Override
	public Sensor updateSensor(Sensor sensor) {
		return em.merge(sensor);
	}

	@Override
	public Sensor findSensor(Long id) {
		return em.find(Sensor.class, id);
	}

}

package fr.amu.terGENREST.services.projectSpecifications;

import javax.ejb.Local;

import fr.amu.terGENREST.entities.projectSpecifications.Sensor;

@Local
public interface SensorManager {

	public Sensor updateSensor(Sensor sensor);

	public Sensor findSensor(Long id);

}

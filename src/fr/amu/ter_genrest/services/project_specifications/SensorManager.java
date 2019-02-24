package fr.amu.ter_genrest.services.project_specifications;

import javax.ejb.Local;

import fr.amu.ter_genrest.entities.project_specifications.Sensor;

@Local
public interface SensorManager {

	public Sensor updateSensor(Sensor sensor);

	public Sensor findSensor(Long id);

}

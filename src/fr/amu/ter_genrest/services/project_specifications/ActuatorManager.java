package fr.amu.ter_genrest.services.project_specifications;

import java.util.List;

import javax.ejb.Local;

import fr.amu.ter_genrest.entities.project_specifications.Actuator;

/**
 * DAO for Actuator: find by id.
 * 
 * @author tsila
 *
 */

@Local
public interface ActuatorManager {

	public Actuator updateActuator(Actuator actuator);

	public Actuator findActuator(Long id);
	
	public List<Actuator> findAll();

}

package fr.amu.terGENREST.services.projectSpecifications;

import java.util.List;

import javax.ejb.Local;

import fr.amu.terGENREST.entities.projectSpecifications.Actuator;

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

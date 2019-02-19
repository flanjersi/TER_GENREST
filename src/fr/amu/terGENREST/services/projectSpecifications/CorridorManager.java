package fr.amu.terGENREST.services.projectSpecifications;

import java.util.List;

import javax.ejb.Local;

import fr.amu.terGENREST.entities.projectSpecifications.Corridor;

/***
 * DAO for Corridor add, update, remove, find by id.
 * 
 * @author tsila
 *
 */

@Local
public interface CorridorManager {

	public Corridor updateCorridor(Corridor corridor);

	public Corridor findCorridor(Long id);

	public List<Corridor> findAllCorridor();

}

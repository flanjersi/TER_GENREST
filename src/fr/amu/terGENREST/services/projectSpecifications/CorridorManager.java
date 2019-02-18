package fr.amu.terGENREST.services.projectSpecifications;

import javax.ejb.Local;

import fr.amu.terGENREST.entities.projectSpecifications.Corridor;

/***
 * DAO for Corridor add, update, remove, find by id.
 * @author tsila
 *
 */

@Local
public interface CorridorManager {

	public void addCorridor(Corridor corridor);

	public Corridor updateCorridor(Corridor corridor);

	public void removeCorridor(Corridor corridor);

	public Corridor findCorridor(Long id);

}

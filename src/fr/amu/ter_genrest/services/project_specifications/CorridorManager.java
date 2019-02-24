package fr.amu.ter_genrest.services.project_specifications;

import java.util.List;

import javax.ejb.Local;

import fr.amu.ter_genrest.entities.project_specifications.Corridor;

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

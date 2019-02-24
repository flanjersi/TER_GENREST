package fr.amu.ter_genrest.services.project_specifications;

import java.util.List;

import javax.ejb.Local;

import fr.amu.ter_genrest.entities.project_specifications.Floor;

/**
 * DAO interface to manipulate ORM Building data
 * @author Hachemi
 *
 */

@Local
public interface FloorManager {

	public Floor updateFloor(Floor floor);
	
	public Floor findById (Long id);

	public List<Floor> findAllFloor();
	
}
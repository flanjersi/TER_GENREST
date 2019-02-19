package fr.amu.terGENREST.services.projectSpecifications;

import java.util.List;
import fr.amu.terGENREST.entities.projectSpecifications.Floor;

/**
 * DAO interface to manipulate ORM Building data
 * @author Hachemi
 *
 */

public interface FloorManager {

	public Floor updateFloor(Floor floor);
	
	public Floor findById (Long id);

	public List<Floor> findAllFloor();
}
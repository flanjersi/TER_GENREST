package fr.amu.terGENREST.services.projectSpecifications;

import java.util.List;

<<<<<<< HEAD
=======
import javax.ejb.Local;

>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
import fr.amu.terGENREST.entities.projectSpecifications.Floor;

/**
 * DAO interface to manipulate ORM Building data
 * @author Hachemi
 *
 */
<<<<<<< HEAD
=======
@Local
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
public interface FloorManager {

	public Floor updateFloor(Floor floor);
	
	public Floor findById (Long id);

	public List<Floor> findAllFloor();
}
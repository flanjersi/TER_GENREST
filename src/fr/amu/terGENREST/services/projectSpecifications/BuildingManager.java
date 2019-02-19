package fr.amu.terGENREST.services.projectSpecifications;

import java.util.List;

<<<<<<< HEAD
=======
import javax.ejb.Local;

>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
import fr.amu.terGENREST.entities.projectSpecifications.Building;

/**
 * DAO interface to manipulate ORM Building data
 * @author Hachemi
 *
 */
<<<<<<< HEAD
public interface BuildingManager {


	public Building updateBuilding(Building building);

=======
@Local
public interface BuildingManager {

	@Deprecated
	public void addBuilding(Building building);

	public Building updateBuilding(Building building);

	@Deprecated
	public void removeBuilding(Building building);
	
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
	public Building findById (Long id);

	public List<Building> findAllBuilding();

<<<<<<< HEAD
}
=======
}
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a

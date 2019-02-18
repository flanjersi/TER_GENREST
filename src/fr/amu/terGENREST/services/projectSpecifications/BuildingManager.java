package fr.amu.terGENREST.services.projectSpecifications;

import java.util.List;

import javax.ejb.Local;

import fr.amu.terGENREST.entities.projectSpecifications.Building;

/**
 * DAO interface to manipulate ORM Building data
 * @author Hachemi
 *
 */
@Local
public interface BuildingManager {

	@Deprecated
	public void addBuilding(Building building);

	public Building updateBuilding(Building building);

	@Deprecated
	public void removeBuilding(Building building);
	
	public Building findById (Long id);

	public List<Building> findAllBuilding();

}

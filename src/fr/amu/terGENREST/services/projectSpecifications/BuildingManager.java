package fr.amu.terGENREST.services.projectSpecifications;

import java.util.List;

import fr.amu.terGENREST.entities.projectSpecifications.Building;

/**
 * DAO interface to manipulate ORM Building data
 * @author Hachemi
 *
 */
public interface BuildingManager {

	public void addBuilding(Building building);

	public Building updateBuilding(Building building);

	public void removeBuilding(Building building);
	
	public Building findById (Long id);

	public List<Building> findAllBuilding();

}

package fr.amu.ter_genrest.services.project_specifications;

import java.util.List;

import javax.ejb.Local;

import fr.amu.ter_genrest.entities.project_specifications.Building;

/**
 * DAO interface to manipulate ORM Building data
 * @author Hachemi
 *
 */
@Local
public interface BuildingManager {

	public Building updateBuilding(Building building);
	
	public Building findById(Long id);

	public List<Building> findAllBuilding();

	public Building findBuildingByFloorId(Long id);
	
}
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

	public Building updateBuilding(Building building);
	
	public Building findById(Long id);

	public List<Building> findAllBuilding();

}
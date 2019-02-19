package fr.amu.terGENREST.services.projectSpecifications;

import java.util.List;

import fr.amu.terGENREST.entities.projectSpecifications.MotherRoom;

public interface MotherRooomManager {
	
	public MotherRoom updateMotherRoom(MotherRoom motherRoom);
	
	public MotherRoom findById (Long id);

	public List<MotherRoom> findAllMotherRoom();
}
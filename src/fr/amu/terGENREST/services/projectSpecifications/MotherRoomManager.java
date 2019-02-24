package fr.amu.terGENREST.services.projectSpecifications;

import java.util.List;

import javax.ejb.Local;

import fr.amu.terGENREST.entities.projectSpecifications.MotherRoom;

@Local
public interface MotherRoomManager {
	
	public MotherRoom updateMotherRoom(MotherRoom motherRoom);
	
	public MotherRoom findById (Long id);

	public List<MotherRoom> findAllMotherRoom();
	
}
package fr.amu.ter_genrest.services.project_specifications;

import java.util.List;

import javax.ejb.Local;

import fr.amu.ter_genrest.entities.project_specifications.MotherRoom;

@Local
public interface MotherRoomManager {
	
	public MotherRoom updateMotherRoom(MotherRoom motherRoom);
	
	public MotherRoom findById (Long id);

	public List<MotherRoom> findAllMotherRoom();
	
}
package fr.amu.ter_genrest.services.project_specifications;

import java.util.List;

import javax.ejb.Local;

import fr.amu.ter_genrest.entities.project_specifications.Room;

@Local
public interface RoomManager {

	public Room updateRoom(Room Room);

	public Room findRoom(Long id);
	
	public List<Room> findAllRooms();

}

package fr.amu.terGENREST.services.projectSpecifications;

import java.util.List;

import javax.ejb.Local;

import fr.amu.terGENREST.entities.projectSpecifications.Room;

@Local
public interface RoomManager {

	public Room updateRoom(Room Room);

	public Room findRoom(Long id);
	
	public List<Room> findAllRooms();

}

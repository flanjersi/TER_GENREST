package fr.amu.terGENREST.services.projectSpecifications;

import fr.amu.terGENREST.entities.projectSpecifications.Room;

public interface RoomManager {

	public Room updateRoom(Room Room);

	public Room findRoom(Long id);

}

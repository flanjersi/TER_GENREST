package fr.amu.ter_genrest.services.project_specifications;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.amu.ter_genrest.entities.project_specifications.Room;

@Stateless
public class RoomManagerImpl implements RoomManager {

	@PersistenceContext(unitName = "database")
	private EntityManager em;

	@Override
	public Room updateRoom(Room Room) {
		return em.merge(Room);
	}

	@Override
	public Room findRoom(Long id) {
		return em.find(Room.class, id);
	}

	@Override
	public List<Room> findAllRooms() {
		
		TypedQuery<Room> q = em.createNamedQuery("Room.findAll", Room.class);
		return q.getResultList();
	}

}

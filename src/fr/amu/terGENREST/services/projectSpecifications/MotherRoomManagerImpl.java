package fr.amu.terGENREST.services.projectSpecifications;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.amu.terGENREST.entities.projectSpecifications.MotherRoom;

@Stateless
public class MotherRoomManagerImpl implements MotherRoomManager {

	@PersistenceContext(unitName = "database")
    private EntityManager em;
	
	@Override
	public MotherRoom updateMotherRoom(MotherRoom MotherRoom) {
		return em.merge(MotherRoom);
	}

	@Override
	public MotherRoom findById(Long id) {
		return em.find(MotherRoom.class, id);
	}

	@Override
	public List<MotherRoom> findAllMotherRoom() {
		TypedQuery<MotherRoom> q = em.createNamedQuery("MotherRoom.findAllMotherRoom", MotherRoom.class);
		return q.getResultList();
	}
}
package fr.amu.terGENREST.services.projectSpecifications;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.amu.terGENREST.entities.projectSpecifications.Building;

@Stateless
public class BuildingManagerImpl implements BuildingManager {
	
	@PersistenceContext(unitName = "database")
    private EntityManager em;

<<<<<<< HEAD
=======
	@Override
	public void addBuilding(Building building) {
		em.persist(building);
	}
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a

	@Override
	public Building updateBuilding(Building building) {
		return em.merge(building);
	}

	@Override
<<<<<<< HEAD
	public List<Building> findAllBuilding() {
		TypedQuery<Building> q = em.createNamedQuery("Building.findAllBuilding", Building.class);
		return q.getResultList();
=======
	public void removeBuilding(Building building) {
		em.remove(em.contains(building) ? building : em.merge(building));
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
	}

	@Override
	public Building findById(Long id) {
		return em.find(Building.class, id);
	}
<<<<<<< HEAD
=======
	
	@Override
	public List<Building> findAllBuilding() {
		TypedQuery<Building> q = em.createNamedQuery("Building.findAllBuilding", Building.class);
		return q.getResultList();
	}
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
}

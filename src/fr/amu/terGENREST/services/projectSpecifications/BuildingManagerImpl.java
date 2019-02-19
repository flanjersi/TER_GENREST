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

	@Override
	public Building updateBuilding(Building building) {
		return em.merge(building);
	}

	public void removeBuilding(Building building) {
		em.remove(em.contains(building) ? building : em.merge(building));
	}

	@Override
	public Building findById(Long id){
		return em.find(Building.class, id);
	}

	@Override
	public List<Building> findAllBuilding() {
		TypedQuery<Building> q = em.createNamedQuery("Building.findAllBuilding", Building.class);
		return q.getResultList();
	}
}
package fr.amu.ter_genrest.services.project_specifications;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.amu.ter_genrest.entities.project_specifications.Building;

@Stateless
public class BuildingManagerImpl implements BuildingManager {
	
	@PersistenceContext(unitName = "database")
    private EntityManager em;

	@Override
	public Building updateBuilding(Building building) {
		return em.merge(building);
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

	@Override
	public Building findBuildingByFloorId(Long id) {
		TypedQuery<Building> q = em.createNamedQuery("Building.findBuildingByFloorId", Building.class).setParameter("idFloor", id);
		
		try {
			return q.getSingleResult();	
		} catch (NoResultException e) {
			return null;
		}
	}
}
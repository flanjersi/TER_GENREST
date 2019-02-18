package fr.amu.terGENREST.services.projectSpecifications;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.amu.terGENREST.entities.projectSpecifications.Data;

@Stateless
public class DataManagerImpl implements DataManager {

	@PersistenceContext(unitName = "database")
	private EntityManager em;

	@Override
	public void removeData(Data data) {
		em.remove(em.contains(data) ? data : em.merge(data));
	}

	@Override
	public Data findData(Long id) {
		return em.find(Data.class, id);
	}

	@Override
	public void addData(Data data) {
		em.persist(data);
	}

	@Override
	public Data updateData(Data data) {
		return em.merge(data);
	}

}

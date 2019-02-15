package fr.amu.terGENREST.services.projectSpecifications;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.amu.terGENREST.entities.projectSpecifications.Apartment;

public class ApartmentManagerImpl implements ApartmentManager {

	@PersistenceContext(unitName = "database")
    private EntityManager em;
	
	@Override
	public Apartment updateApartment(Apartment apartment) {
		return em.merge(apartment);
	}

	@Override
	public Apartment findById(Long id) {
		return em.find(Apartment.class, id);
	}

	@Override
	public List<Apartment> findAllApartment() {
		TypedQuery<Apartment> q = em.createNamedQuery("apartment.findAllApartment", Apartment.class);
		return q.getResultList();
	}
}
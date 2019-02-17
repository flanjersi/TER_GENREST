package fr.amu.terGENREST.services.projectSpecifications;

import java.util.List;

import javax.ejb.Local;

import fr.amu.terGENREST.entities.projectSpecifications.Apartment;

@Local
public interface ApartmentManager {
	
	public Apartment updateApartment(Apartment apartment);
	
	public Apartment findById (Long id);

	public List<Apartment> findAllApartment();
}
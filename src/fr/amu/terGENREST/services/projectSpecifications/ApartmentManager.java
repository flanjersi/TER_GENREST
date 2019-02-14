package fr.amu.terGENREST.services.projectSpecifications;

import java.util.List;

import fr.amu.terGENREST.entities.projectSpecifications.Apartment;

public interface ApartmentManager {
	public Apartment updateFloor(Apartment apartment);
	
	public Apartment findById (Long id);

	public List<Apartment> findAllApartment();
}

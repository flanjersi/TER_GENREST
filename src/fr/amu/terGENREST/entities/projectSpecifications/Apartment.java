package fr.amu.terGENREST.entities.projectSpecifications;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Table(name = "Apartment")
@Entity
@NamedQueries({
	@NamedQuery(name = "apartment.findAllApartment", query = "SELECT a FROM Apartment a"),
})
public class Apartment {
	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Basic(optional = false) @Size(min = 1, max = 10)
	@Column(nullable = false)
	private int apartmentNumber;
	
//	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},  orphanRemoval = true)
//	private List<Room> appartmentRoom = new ArrayList<Room>();

	public Apartment() {
	}

	public Apartment(int apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getApartmentNumber() {
		return apartmentNumber;
	}

	public void setApartmentNumber(int apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	@Override
	public String toString() {
		return "Apartment [id=" + id + ", apartmentNumber=" + apartmentNumber + "]";
	}

}

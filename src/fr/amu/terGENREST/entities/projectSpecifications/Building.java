package fr.amu.terGENREST.entities.projectSpecifications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Table(name = "Building")
@Entity
@NamedQueries ({
	@NamedQuery(name="Building.findAllBuilding", 
				query = "SELECT Bld FROM Building Bld")
})
public class Building implements Serializable{
	private static final long serialVersionUID = 10000L;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Basic(optional = false)
	@Column(nullable = false)
	private int number;
	
	@Basic(optional = false) @Size(min = 1, max = 50)
	@Column(nullable = false, length = 50)
	private String street;
	
	@Basic(optional = false) @Size(min = 1, max = 20)
	@Column(nullable = false, length = 20)
	private String city;
	
	@Basic(optional = false)
	@Column(nullable = false)
	private int zipCode;
	
	@Basic(optional = false) @Size(min = 1, max = 20)
	@Column(nullable = false, length = 50)
	private String country;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,  orphanRemoval = true)
	private List<Floor> buildingFloor = new ArrayList<Floor>();
	private List<Apartment> floorApartment = new ArrayList<Apartment>();
	
	public Building() {
		
	}

	public Building(int number, String street, String city, int zipCode, String country) {
		this.number = number;
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
		this.country = country;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<Floor> getBuildingFloor() {
		return buildingFloor;
	}

	public void setBuildingFloor(List<Floor> buildingFloor) {
		this.buildingFloor = buildingFloor;
	}
	
	public void addFloor(Floor floor) {
		buildingFloor.add(floor);
	}
	
	public void removeFloor(Floor floor) {
		buildingFloor.remove(floor);
	}
	
	public void addAppartment(Apartment apartment) {
		floorApartment.add(apartment);
	}
	
	public void removeAppartment(Apartment apartment) {
		floorApartment.remove(apartment);
	}
	
	@Override
	public String toString() {
		return "Building [id=" + id + ", number=" + number + ", street=" + street + ", city=" + city + ", zipCode="
				+ zipCode + ", country=" + country + "]";
	}	
}
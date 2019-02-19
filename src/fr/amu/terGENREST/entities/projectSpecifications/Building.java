package fr.amu.terGENREST.entities.projectSpecifications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
=======
import javax.persistence.CascadeType;
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
<<<<<<< HEAD
import javax.validation.constraints.Size;

=======
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a

/**
 * Building have one or many floors. Floors have one or many
 * Apartment(List<Apartment>), and Corridor (List<Corridor>) Apartment have one
 * or many room (List<Room>). Corridor and room have one or many
<<<<<<< HEAD
 * sensor(List<Sensor>),Actuator(List<Actuator).
 * Building->Floors->Apartments and Corridor->Apartment->Room->Sensors and Actuators.
=======
 * sensor(List<Sensor>),Actuator(List<Actuator). Building->Floors->Apartments
 * and Corridor->Apartment->Room->Sensors and Actuators.
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
 *
 */
@Table(name = "Building")
@Entity
@NamedQueries({ @NamedQuery(name = "Building.findAllBuilding", query = "SELECT Bld FROM Building Bld") })
public class Building implements Serializable {
<<<<<<< HEAD
=======

>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
	private static final long serialVersionUID = 10000L;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
<<<<<<< HEAD
	
=======

>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
	@Embedded
	private Address address;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Floor> buildingFloor = new ArrayList<Floor>();
<<<<<<< HEAD
	
	@Basic(optional = false) 
	@Column(nullable = false,length=20) 
	@Size(min = 1, max = 20) 
	private String type; 


	public Building() {
	}
	
	public Building(String type, Address address) {
		this.type = type;
		this.address = address;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

=======

	public Building() {
	}

	public Building(Address address) {
		this.address = address;
	}

	public long getId() {
		return id;
	}

>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
<<<<<<< HEAD
		return "Building [id=" + id + ", address=" + address + ", buildingFloor=" + buildingFloor + ", type=" + type
				+ "]";
	}
=======
		return "Building [id=" + id + ", address=" + address + ", buildingFloor=" + buildingFloor + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Building other = (Building) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
}
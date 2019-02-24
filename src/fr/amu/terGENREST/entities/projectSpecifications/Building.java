package fr.amu.terGENREST.entities.projectSpecifications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.validation.constraints.Size;

/**
 * Building have one or many floors. Floors have one or many
 * Apartment(List<Apartment>), and Corridor (List<Corridor>) Apartment have one
 * or many room (List<Room>). Corridor and room have one or many
 * sensor(List<Sensor>),Actuator(List<Actuator). Building->Floors->Apartments
 * and Corridor->Apartment->Room->Sensors and Actuators.
 *
 */
@Table(name = "Building")
@Entity
@NamedQueries({ 
	@NamedQuery(name = "Building.findAllBuilding", query = "SELECT Bld FROM Building Bld"),
	@NamedQuery(name = "Building.findBuildingByFloorId", query = "SELECT bld FROM Building bld join bld.floors fl WHERE fl.id = :idFloor"
), 	
})
public class Building implements Serializable {

	private static final long serialVersionUID = 10000L;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Embedded
	private Address address;
	
	@Basic(optional = false) 
	@Column(nullable = false,length=20) 
	@Size(min = 1, max = 20) 
	private String type; 

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Floor> floors = new ArrayList<Floor>();

	public Building() {
	}

	

	public Building(String type,Address address) {
		this.type = type;
		this.address = address;
	}
	

	public Long getId() {
		return id;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Floor> getFloors() {
		return floors;
	}

	public void setFloors(List<Floor> floors) {
		this.floors = floors;
	}

	public void addFloor(Floor floor) {
		floors.add(floor);
	}

	public void removeFloor(Floor floor) {
		floors.remove(floor);
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}


	@Override
	public String toString() {
		return "Building [id=" + id + ", address=" + address + ", type=" + type + ", floors=" + floors + "]";
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
}
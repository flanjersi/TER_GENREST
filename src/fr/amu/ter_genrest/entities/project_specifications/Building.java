package fr.amu.ter_genrest.entities.project_specifications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

@NamedQueries({ 
	@NamedQuery(
			name = "Building.findAllBuilding", 
			query = "SELECT Bld FROM Building Bld"),
	@NamedQuery(
			name = "Building.findBuildingByFloorId", 
			query = "SELECT bld FROM Building bld join bld.floors fl WHERE fl.id = :idFloor"), 	
})

@Table(name = "Building")
@Entity
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

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE, CascadeType.MERGE,CascadeType.PERSIST }, orphanRemoval=true)
	private Set<Floor> floors = new HashSet<>();

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

	public Set<Floor> getFloors() {
		return floors;
	}

	public void setFloors(Set<Floor> floors) {
		this.floors = floors;
	}

	public void addFloor(Floor floor) {
		this.floors.add(floor);
	}

	public void removeFloor(Floor floor) {
		
		if(this.floors.size() == 1 && this.floors.removeIf(f -> f.getId().equals(floor.getId()))) {
			this.floors.clear();
		}
		
		this.floors.remove(floor);
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
}
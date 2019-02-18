package fr.amu.terGENREST.entities.projectSpecifications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
@NamedQueries({ @NamedQuery(name = "Building.findAllBuilding", query = "SELECT Bld FROM Building Bld") })
public class Building implements Serializable {

	private static final long serialVersionUID = 10000L;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Embedded
	private Address address;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Floor> buildingFloor = new ArrayList<Floor>();

	public Building() {
	}

	public Building(Address address) {
		this.address = address;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
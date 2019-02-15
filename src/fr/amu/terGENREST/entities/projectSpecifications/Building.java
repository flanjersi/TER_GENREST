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
 * sensor(List<Sensor>),Actuator(List<Actuator).
 * Building->Floors->Apartments and Corridor->Apartment->Room->Sensors and Actuators.
 *
 */
@Table(name = "Building")
@Entity
@NamedQueries({ @NamedQuery(name = "Building.findAllBuilding", query = "SELECT Bld FROM Building Bld") })
public class Building implements Serializable {
	private static final long serialVersionUID = 10000L;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Embedded
	private Address address;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Floor> buildingFloor = new ArrayList<Floor>();

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE, CascadeType.MERGE,
			CascadeType.PERSIST }, orphanRemoval = true)
	private List<Apartment> floorAprtment = new ArrayList<Apartment>();

	public Building() {

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

	public List<Apartment> getFloorAprtment() {
		return floorAprtment;
	}

	public void setFloorAprtment(List<Apartment> floorAprtment) {
		this.floorAprtment = floorAprtment;
	}

	public void addFloor(Floor floor) {
		buildingFloor.add(floor);
	}

	public void removeFloor(Floor floor) {
		buildingFloor.remove(floor);
	}

	public void addAppartment(Apartment apartment) {
		floorAprtment.add(apartment);
	}

	public void removeAppartment(Apartment apartment) {
		floorAprtment.remove(apartment);
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Building(Address address, List<Floor> buildingFloor, List<Apartment> floorAprtment) {
		super();
		this.address = address;
		this.buildingFloor = buildingFloor;
		this.floorAprtment = floorAprtment;
	}

	public Building(List<Floor> buildingFloor, List<Apartment> floorAprtment) {
		super();
		this.buildingFloor = buildingFloor;
		this.floorAprtment = floorAprtment;
	}

	public Building(List<Apartment> floorAprtment) {
		super();
		this.floorAprtment = floorAprtment;
	}

}
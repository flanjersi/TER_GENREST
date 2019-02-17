package fr.amu.terGENREST.entities.projectSpecifications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/***
 * 
 * House have one or many FloorHouse. Identified by id and address.
 * 
 * FloorHouse have one or many room(List<Room>),and Corridor (List<Corridor>).
 * Corridor and room have one or many
 * sensor(List<Sensor>),Actuator(List<Actuator). House->FloorHouse->rooms and
 * Corridors->Sensors and Actuators.
 *
 *
 * 
 * @author tsila
 *
 */

@NamedQueries({ @NamedQuery(name = "House.findAll", query = "select h from House h"), })
@Entity
@Table(name = "House")
public class House implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Embedded
	private Address address;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, orphanRemoval = true)
	List<FloorHouse> houseFloor = new ArrayList<FloorHouse>();

	public void addFloor(FloorHouse h) {
		houseFloor.add(h);

	}

	public void removeFloor(FloorHouse h) {
		houseFloor.remove(h);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public House(Address address) {
		super();
		this.address = address;
	}

	public House() {
		super();

	}

	public List<FloorHouse> getHouseFloor() {
		return houseFloor;
	}

	public void setHouseFloor(List<FloorHouse> houseFloor) {
		this.houseFloor = houseFloor;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public House(Address address, List<FloorHouse> houseFloor) {
		super();
		this.address = address;
		this.houseFloor = houseFloor;
	}

	public House(List<FloorHouse> houseFloor) {
		super();
		this.houseFloor = houseFloor;
	}

}

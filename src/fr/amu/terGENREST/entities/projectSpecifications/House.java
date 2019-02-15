package fr.amu.terGENREST.entities.projectSpecifications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * 
 * House have one or many rooms, and corridors. Identified by id and address.
 * 
 * House have one floor. Floors have one or many Apartment(List<Apartment>), and
 * Corridor (List<Corridor>) Apartment have one or many room (List<Room>).
 * Corridor and room have one or many sensor(List<Sensor>),Actuator(List<Actuator). 
 * House->Floors->Apartments and Corridors->Apartment->Room->Sensors and Actuators.
 *
 *
 * 
 * @author tsila
 *
 */

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

	List<Room> houseRoom = new ArrayList<Room>();

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

	public House(Address address, List<Room> houseRoom) {
		super();
		this.address = address;
		this.houseRoom = houseRoom;
	}

	public House(List<Room> houseRoom) {
		super();
		this.houseRoom = houseRoom;
	}

}

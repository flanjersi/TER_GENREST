package fr.amu.terGENREST.entities.projectSpecifications;


import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * 
 * House have one or many room,corridor, captor, camera. Identified by id and
 * address.
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

}

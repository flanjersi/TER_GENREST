package fr.amu.terGENREST.entities.projectSpecifications;


import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * Address represented by street, city, contry.
 * Embedable class depends on House, or Buildings.
 * 
 * @author tsila
 *
 */

@Embeddable()
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;
	private String street;
	private String city;
	private String country;

	public Address() {
		super();
	}

	public Address(String street, String city, String country) {
		this.street = street;
		this.city = city;
		this.country = country;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Address(" + street + "," + city + "," + country + ")";
	}
}
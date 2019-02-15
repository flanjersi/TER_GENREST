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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Actuator represented by: id,latitude (not null)longitude (not null),model
 * (not null),brand (not null),reference (not null) state (not null)
 * 
 * @author tsila
 *
 */

@NamedQueries({ @NamedQuery(name = "Actuator.findAll", query = "select a from Actuator a"), })
@Entity
@Table(name = "Actuator")
public class Actuator implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "latitude", nullable = false)
	@Size(min = 1, max = 200)
	private double latitude;

	@Column(name = "longitude", nullable = false)
	@Size(min = 1, max = 200)
	private double longitude;

	@Column(name = "model", nullable = false)
	@Size(min = 1, max = 200)
	private String model;

	@Column(name = "brand", nullable = false)
	@Size(min = 1, max = 200)
	private String brand;

	@Column(name = "reference", nullable = false)
	@Size(min = 1, max = 200)
	private String reference;

	@Column(name = "state", nullable = false)
	@Size(min = 1, max = 200)
	private String state;

	@Basic
	private String unitData;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, orphanRemoval = true)
	@Size(min = 1, max = 200)
	List<Data> dataList = new ArrayList<Data>();

	public Long getId() {
		return id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Actuator(double latitude, double longitude, String model, String brand, String reference, String state) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.model = model;
		this.brand = brand;
		this.reference = reference;
		this.state = state;
	}

	public Actuator(double latitude, double longitude, String model, String brand, String reference) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.model = model;
		this.brand = brand;
		this.reference = reference;
	}

	public Actuator(double latitude, double longitude, String model) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.model = model;
	}

	public Actuator(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Actuator(double latitude) {
		super();
		this.latitude = latitude;
	}

	public Actuator() {
		super();
	}

}

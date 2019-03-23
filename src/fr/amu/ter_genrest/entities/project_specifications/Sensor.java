package fr.amu.ter_genrest.entities.project_specifications;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Sensor represented by: id,latitude (not null)longitude (not null),model (not
 * null),brand (not null),reference (not null) state (not null)
 * 
 * @author tsila
 *
 */

@NamedQueries({ 
	@NamedQuery(
			name = "Sensor.findAll", 
			query = "select s from Sensor s"),
})

@Entity
@Table(name = "Sensor")
public class Sensor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", nullable = false)
	@Size(min = 1, max = 200)
	private String name;
	
	@Column(name = "latitude", nullable = false)
	private double latitude;

	@Column(name = "longitude", nullable = false)
	private double longitude;

	@Column(name = "model", nullable = false)
	@Size(min = 1, max = 200)
	private String model;

	@Column(name = "quantityKind", nullable = false)
	@Size(min = 1, max = 200)
	private String quantityKind;

	@Column(name = "unitData", nullable = false)
	@Size(min = 1, max = 200)
	private String unitData;

	
	public Sensor(String name, double latitude, double longitude, String model, String unitData, String quantityKind) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.model = model;
		this.unitData = unitData;
		this.quantityKind = quantityKind;
	}

	public Sensor() {
	}
	
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

	
	public String getUnitData() {
		return unitData;
	}

	public void setUnitData(String unitData) {
		this.unitData = unitData;
	}
	
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantityKind() {
		return quantityKind;
	}

	public void setQuantityKind(String quantityKind) {
		this.quantityKind = quantityKind;
	}

	@Override
	public String toString() {
		return "Sensor [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", model=" + model
				+ ", quantityKind=" + quantityKind + ", unitData=" + unitData + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((quantityKind == null) ? 0 : quantityKind.hashCode());
		result = prime * result + ((unitData == null) ? 0 : unitData.hashCode());
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
		Sensor other = (Sensor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (quantityKind == null) {
			if (other.quantityKind != null)
				return false;
		} else if (!quantityKind.equals(other.quantityKind))
			return false;
		if (unitData == null) {
			if (other.unitData != null)
				return false;
		} else if (!unitData.equals(other.unitData))
			return false;
		return true;
	}

	
}
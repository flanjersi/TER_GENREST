package fr.amu.terGENREST.entities.projectSpecifications;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
 * Sensor represented by: id,latitude (not null)longitude (not null),model (not
 * null),brand (not null),reference (not null) state (not null)
 * 
 * @author tsila
 *
 */

@NamedQueries({ @NamedQuery(name = "Sensor.findAll", query = "select s from Sensor s"), })
@Entity
@Table(name = "Sensor")
public class Sensor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "latitude", nullable = false)
	private double latitude;

	@Column(name = "longitude", nullable = false)
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

	@Column(name = "unitData", nullable = false)
	@Size(min = 1, max = 200)
	private String unitData;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, orphanRemoval = true)
	private List<Data> dataList;

	public Sensor(double latitude, double longitude, String model, String brand, String reference, String state,
			String unitData) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.model = model;
		this.brand = brand;
		this.reference = reference;
		this.state = state;
		this.unitData = unitData;
	}

	public Sensor() {
		super();
	}
	
	public void addData(Data a) {
		dataList.add(a);
	}

	public void removeData(Data a) {
		dataList.remove(a);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getUnitData() {
		return unitData;
	}

	public void setUnitData(String unitData) {
		this.unitData = unitData;
	}

	public List<Data> getDataList() {
		return dataList;
	}

	public void setDataList(List<Data> dataList) {
		this.dataList = dataList;
	}

	@Override
	public String toString() {
		return "Sensor [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", model=" + model
				+ ", brand=" + brand + ", reference=" + reference + ", state=" + state + ", unitData=" + unitData
				+ ", dataList=" + dataList + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
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
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (unitData == null) {
			if (other.unitData != null)
				return false;
		} else if (!unitData.equals(other.unitData))
			return false;
		return true;
	}

	

	
}

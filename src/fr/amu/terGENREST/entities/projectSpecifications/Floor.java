package fr.amu.terGENREST.entities.projectSpecifications;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
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
import javax.transaction.Transactional;


@Table(name = "Floor")
@Entity
@NamedQueries({
	@NamedQuery(name = "floor.findAllFloor", query = "SELECT fl FROM Floor fl"),
})
public class Floor {
	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Basic(optional = false)
	@Column(nullable = false)
	private int floorNumber;
	
	@OneToMany(fetch=FetchType.EAGER, cascade = { CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval=true)
	private List<Apartment> buildingFloor = new ArrayList<Apartment>();
	
	public Floor() {
	}

	public Floor(int floorNumber) {
		super();
		this.floorNumber = floorNumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public List<Apartment> getBuildingFloor() {
		return buildingFloor;
	}

	public void setBuildingFloor(List<Apartment> buildingFloor) {
		this.buildingFloor = buildingFloor;
	}

	@Override
	public String toString() {
		return "Floor [id=" + id + ", floorNumber=" + floorNumber + ", buildingFloor=" + buildingFloor + "]";
	}
}
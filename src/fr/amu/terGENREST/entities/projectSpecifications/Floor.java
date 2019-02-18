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

@Table(name = "Floor")
@Entity
@NamedQueries({ @NamedQuery(name = "floor.findAllFloor", query = "SELECT fl FROM Floor fl"), })
public class Floor {
	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Basic(optional = false)
	@Column(nullable = false)
	private int floorNumber;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE, CascadeType.MERGE,
			CascadeType.PERSIST }, orphanRemoval = true)
	private List<MotherRoom> buildingMotherRoom = new ArrayList<MotherRoom>();

	public void addMotherRoom(MotherRoom m) {
		buildingMotherRoom.add(m);

	}

	public void removeMotherRoom(MotherRoom m) {
		buildingMotherRoom.remove(m);

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

	public List<MotherRoom> getBuildingFloor() {
		return buildingMotherRoom;
	}

	public void setBuildingFloor(List<MotherRoom> buildingMotherRoom) {
		this.buildingMotherRoom = buildingMotherRoom;
	}

	@Override
	public String toString() {
		return "Floor [id=" + id + ", floorNumber=" + floorNumber + ", buildingFloor=" + buildingMotherRoom + "]";
	}

	public Floor() {
	}

	public Floor(int floorNumber) {
		super();
		this.floorNumber = floorNumber;
	}

}
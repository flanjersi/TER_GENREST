package fr.amu.terGENREST.entities.projectSpecifications;

<<<<<<< HEAD
import java.io.Serializable;
=======
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
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

<<<<<<< HEAD

@Table(name = "Floor")
@Entity
@NamedQueries({
	@NamedQuery(name = "floor.findAllFloor", query = "SELECT fl FROM Floor fl"),
})
public class Floor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Basic(optional = false)
	@Column(nullable = false)
	private Integer floorNumber;
	
	@OneToMany(fetch=FetchType.EAGER, cascade = { CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval=true)
	private List<MotherRoom> floorMotherRoom = new ArrayList<MotherRoom>();
	
	public Floor() {
	}

	public Floor(Integer floorNumber) {
		this.floorNumber = floorNumber;
	}
=======
@Table(name = "Floor")
@Entity
@NamedQueries({ @NamedQuery(name = "floor.findAllFloor", query = "SELECT fl FROM Floor fl"), })
public class Floor {
	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Basic(optional = false)
	@Column(nullable = false)
	private int floorNumber;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE, CascadeType.MERGE,
			CascadeType.PERSIST }, orphanRemoval = true)
	private List<MotherRoom> buildingMotherRoom = new ArrayList<MotherRoom>();

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE, CascadeType.MERGE,
			CascadeType.PERSIST }, orphanRemoval = true)
	private List<Corridor> corridors = new ArrayList<Corridor>();

	public Floor() {
	}

	public Floor(int floorNumber) {
		super();
		this.floorNumber = floorNumber;
	}
	
	public void addMotherRoom(MotherRoom m) {
		buildingMotherRoom.add(m);

	}

	public void removeMotherRoom(MotherRoom m) {
		buildingMotherRoom.remove(m);
	}
	
	public void addCorridor(Corridor corridor) {
		corridors.add(corridor);
	}
	
	public void removeCorridor(Corridor corridor) {
		corridors.remove(corridor);
	}
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a

	public Long getId() {
		return id;
	}

<<<<<<< HEAD
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(Integer floorNumber) {
		this.floorNumber = floorNumber;
	}

	public List<MotherRoom> getfloorMotherRoom() {
		return floorMotherRoom;
	}

	public void setfloorMotherRoom(List<MotherRoom> floorMotherRoom) {
		this.floorMotherRoom = floorMotherRoom;
	}
	
	public void addMotherRooom(MotherRoom motherRoom) {
		floorMotherRoom.add(motherRoom);
	}

	public void removeMotherRoom(MotherRoom motherRoom) {
		floorMotherRoom.remove(motherRoom);
=======

	public int getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}
	
	public List<MotherRoom> getBuildingMotherRoom() {
		return buildingMotherRoom;
	}

	public void setBuildingMotherRoom(List<MotherRoom> buildingMotherRoom) {
		this.buildingMotherRoom = buildingMotherRoom;
	}

	public List<Corridor> getCorridors() {
		return corridors;
	}

	public void setCorridors(List<Corridor> corridors) {
		this.corridors = corridors;
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
	}

	@Override
	public String toString() {
<<<<<<< HEAD
		return "Floor [id=" + id + ", floorNumber=" + floorNumber + ", floorMotherRoom=" + floorMotherRoom + "]";
	}
=======
		return "Floor [id=" + id + ", floorNumber=" + floorNumber + ", buildingMotherRoom=" + buildingMotherRoom
				+ ", corridors=" + corridors + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + floorNumber;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Floor other = (Floor) obj;
		if (floorNumber != other.floorNumber)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
}
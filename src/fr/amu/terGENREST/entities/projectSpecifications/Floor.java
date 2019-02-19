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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


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

	public Long getId() {
		return id;
	}

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
	}

	@Override
	public String toString() {
		return "Floor [id=" + id + ", floorNumber=" + floorNumber + ", floorMotherRoom=" + floorMotherRoom + "]";
	}
}
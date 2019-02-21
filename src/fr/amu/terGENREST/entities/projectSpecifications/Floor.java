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
	@NamedQuery(name = "floor.findAllFloor", query = "SELECT fl FROM Floor fl")
})

public class Floor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5036343810149445657L;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Basic(optional = false)
	@Column(nullable = false, unique = true)
	private int floorNumber;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE, CascadeType.MERGE,
			CascadeType.PERSIST }, orphanRemoval = true)
	private List<MotherRoom> motherRooms = new ArrayList<MotherRoom>();

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
		motherRooms.add(m);

	}

	public void removeMotherRoom(MotherRoom m) {
		motherRooms.remove(m);
	}
	
	public void addCorridor(Corridor corridor) {
		corridors.add(corridor);
	}
	
	public void removeCorridor(Corridor corridor) {
		corridors.remove(corridor);
	}

	public Long getId() {
		return id;
	}
	
	public int getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}
	
	public List<MotherRoom> getMotherRooms() {
		return motherRooms;
	}

	public void setMotherRooms(List<MotherRoom> motherRooms) {
		this.motherRooms = motherRooms;
	}

	public List<Corridor> getCorridors() {
		return corridors;
	}

	public void setCorridors(List<Corridor> corridors) {
		this.corridors = corridors;
	}

	

	@Override
	public String toString() {
		return "Floor [id=" + id + ", floorNumber=" + floorNumber + ", motherRooms=" + motherRooms + ", corridors="
				+ corridors + "]";
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

	
	
}
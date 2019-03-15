package fr.amu.ter_genrest.entities.project_specifications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


@NamedQueries({ 
	@NamedQuery(name = "floor.findAllFloor", query = "SELECT fl FROM Floor fl")
})

@Table(name = "Floor")
@Entity
public class Floor implements Serializable{

	private static final long serialVersionUID = 5036343810149445657L;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Basic(optional = false)
	@Column(nullable = false)
	private int floorNumber;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE, CascadeType.MERGE,
			CascadeType.PERSIST }, orphanRemoval = true)
	private Set<MotherRoom> motherRooms = new HashSet<MotherRoom>();

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE, CascadeType.MERGE,
			CascadeType.PERSIST }, orphanRemoval = true)
	private Set<Corridor> corridors = new HashSet<Corridor>();

	public Floor() {
	}

	public Floor(int floorNumber) {
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
	
	public Set<MotherRoom> getMotherRooms() {
		return motherRooms;
	}

	public void setMotherRooms(Set<MotherRoom> motherRooms) {
		this.motherRooms = motherRooms;
	}

	public Set<Corridor> getCorridors() {
		return corridors;
	}

	public void setCorridors(Set<Corridor> corridors) {
		this.corridors = corridors;
	}

	@Override
	public String toString() {
		return "Floor [id=" + id + ", floorNumber=" + floorNumber + ", motherRooms=" + motherRooms + ", corridors="
				+ corridors + "]";
	}
}
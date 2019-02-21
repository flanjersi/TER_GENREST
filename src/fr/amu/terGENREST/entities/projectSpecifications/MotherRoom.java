package fr.amu.terGENREST.entities.projectSpecifications;

import java.io.Serializable;
import java.util.ArrayList;
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
 * 
 * MotherRoom contains a id, type,
 * 
 * @author tsila
 *
 */
@Table(name = "MotherRoom")
@Entity
@NamedQueries({ @NamedQuery(name = "MotherRoom.findAllMotherRoom", query = "SELECT m FROM MotherRoom m") })
public class MotherRoom implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "type", nullable = false)
	@Size(min = 1, max = 200)
	private String type;

	@Column(name = "numberMotherRoom", nullable = false, unique = true)
	private int numberMotherRoom;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Room> rooms = new ArrayList<Room>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Corridor> corridors = new ArrayList<Corridor>();

	public MotherRoom() {
		super();
	}
	
	public MotherRoom(String type, int numberMotherRoom) {
		this.type = type;
		this.numberMotherRoom = numberMotherRoom;
	}

	
	public void addRoom(Room r) {
		rooms.add(r);

	}

	public void removeRoom(Room r) {
		rooms.remove(r);

	}

	public void addCorridor(Corridor c) {
		corridors.add(c);

	}

	public void removeCorridor(Corridor c) {
		corridors.remove(c);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumberMotherRoom() {
		return numberMotherRoom;
	}

	public void setNumberMotherRoom(int numberMotherRoom) {
		this.numberMotherRoom = numberMotherRoom;
	}

	public Long getId() {
		return id;
	}


	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public List<Corridor> getCorridors() {
		return corridors;
	}

	public void setCorridors(List<Corridor> corridors) {
		this.corridors = corridors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + numberMotherRoom;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		MotherRoom other = (MotherRoom) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numberMotherRoom != other.numberMotherRoom)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	
	
}
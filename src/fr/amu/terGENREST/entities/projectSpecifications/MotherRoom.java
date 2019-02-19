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

@Table(name = "motherRoom")
@Entity
@NamedQueries({
	@NamedQuery(name = "MotherRoom.findAllmotherRoom", query = "SELECT a FROM MotherRoom a"),
})
public class MotherRoom implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Basic(optional = false)
	@Column(nullable = false)
	private int motherRoomNumber;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},  orphanRemoval = true)
	private List<Room> appartmentRoom = new ArrayList<Room>();

	public MotherRoom() {
	}

	public MotherRoom(int motherRoomNumber) {
		this.motherRoomNumber = motherRoomNumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getmotherRoomNumber() {
		return motherRoomNumber;
	}

	public void setmotherRoomNumber(int motherRoomNumber) {
		this.motherRoomNumber = motherRoomNumber;
	}
	
	public List<Room> getAppartmentRoom() {
		return appartmentRoom;
	}

	public void setAppartmentRoom(List<Room> appartmentRoom) {
		this.appartmentRoom = appartmentRoom;
	}

	public void addRoom(Room room) {
		appartmentRoom.add(room);
	}

	public void removeRoom(Room room) {
		appartmentRoom.remove(room);
	}

	@Override
	public String toString() {
		return "motherRoom [id=" + id + ", motherRoomNumber=" + motherRoomNumber + ", appartmentRoom=" + appartmentRoom
				+ "]";
	}
}
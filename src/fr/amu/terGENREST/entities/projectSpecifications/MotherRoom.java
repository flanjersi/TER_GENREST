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

	@Column(name = "numberMotherRoom", nullable = false)
	private int numberMotherRoom;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Room> listRoom = new ArrayList<Room>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Corridor> listCorridor = new ArrayList<Corridor>();

	public void addRoom(Room r) {
		listRoom.add(r);

	}

	public void removeRoom(Room r) {
		listRoom.remove(r);

	}

	public void addCorridor(Corridor c) {
		listCorridor.add(c);

	}

	public void removeCorridor(Corridor c) {
		listCorridor.remove(c);

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

	public void setId(Long id) {
		this.id = id;
	}

	public List<Room> getListRoom() {
		return listRoom;
	}

	public void setListRoom(List<Room> listRoom) {
		this.listRoom = listRoom;
	}

	public List<Corridor> getListCorridor() {
		return listCorridor;
	}

	public void setListCorridor(List<Corridor> listCorridor) {
		this.listCorridor = listCorridor;
	}

	public MotherRoom(String type, int numberMotherRoom) {
		super();
		this.type = type;
		this.numberMotherRoom = numberMotherRoom;
	}

	public MotherRoom() {

	}

	
}

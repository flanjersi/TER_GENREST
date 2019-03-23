package fr.amu.ter_genrest.entities.project_specifications;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
 * @author tsila
 *
 */


@NamedQueries({ 
	@NamedQuery(
			name = "Zone.findAllZone", 
			query = "SELECT z FROM Zone z")
})

@Entity
@Table(name = "Zone")
public class Zone implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "type", nullable = false)
	@Size(min = 1, max = 200)
	private String type;

	@Column(name = "name", nullable = false)
	@Size(min = 1, max = 200)
	private String name;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Room> rooms = new HashSet<Room>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Corridor> corridors = new HashSet<Corridor>();

	public Zone() {
	}
	
	public Zone(String type, String name) {
		this.type = type;
		this.name = name;
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

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}


	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	public Set<Corridor> getCorridors() {
		return corridors;
	}

	public void setCorridors(Set<Corridor> corridors) {
		this.corridors = corridors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((corridors == null) ? 0 : corridors.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rooms == null) ? 0 : rooms.hashCode());
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
		Zone other = (Zone) obj;
		if (corridors == null) {
			if (other.corridors != null)
				return false;
		} else if (!corridors.equals(other.corridors))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (rooms == null) {
			if (other.rooms != null)
				return false;
		} else if (!rooms.equals(other.rooms))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Zone [id=" + id + ", type=" + type + ", name=" + name + ", rooms=" + rooms + ", corridors=" + corridors
				+ "]";
	}

		
}
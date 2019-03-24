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

/***
 * Room identified by id (auto-generated),numberRoom (null),type (not
 * null)sensors : Set<Sensor> (initialise to empty) actuators :
 * Set<Actuator>(initialise to empty)
 * 
 * @author tsila
 *
 */

@NamedQueries({ 
	@NamedQuery(
			name = "Room.findAll", 
			query = "select r from Room r"), 
})

@Entity
@Table(name = "Room")
public class Room implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", nullable = false)
	@Size(min = 1, max = 200)
	private String name;

	@Column(name = "type", nullable = false)
	@Size(min = 1, max = 200)
	private String type;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
	private Set<Sensor> sensors = new HashSet<Sensor>();

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
	private Set<Actuator> actuators = new HashSet<Actuator>();

	public Room() {
	}
	
	public Room(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public void addSensor(Sensor s) {
		sensors.add(s);
	}

	public void removeSensor(Sensor s) {
		sensors.remove(s);
	}

	public void addActuator(Actuator a) {
		actuators.add(a);
	}

	public void removeActuator(Actuator a) {
		actuators.remove(a);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(Set<Sensor> sensors) {
		this.sensors = sensors;
	}

	public Long getId() {
		return id;
	}

	public Set<Actuator> getActuators() {
		return actuators;
	}

	public void setActuators(Set<Actuator> actuators) {
		this.actuators = actuators;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", numberRoom=" + name + ", type=" + type + ", sensors=" + sensors
				+ ", actuators=" + actuators + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actuators == null) ? 0 : actuators.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sensors == null) ? 0 : sensors.hashCode());
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
		Room other = (Room) obj;
		if (actuators == null) {
			if (other.actuators != null)
				return false;
		} else if (!actuators.equals(other.actuators))
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
		if (sensors == null) {
			if (other.sensors != null)
				return false;
		} else if (!sensors.equals(other.sensors))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	
}

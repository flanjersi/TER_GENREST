package fr.amu.terGENREST.entities.projectSpecifications;

<<<<<<< HEAD

=======
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
<<<<<<< HEAD
=======
import javax.persistence.GenerationType;
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/***
 * Room identified by id (auto-generated),numberRoom (null),type (not
 * null)sensors : List<Sensor> (initialise to empty) actuators :
 * List<Actuator>(initialise to empty)
 * 
 * @author tsila
 *
 */

@NamedQueries({ @NamedQuery(name = "Room.findAll", query = "select r from Room r"), })
@Entity
@Table(name = "Room")
public class Room implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
<<<<<<< HEAD
	@GeneratedValue
	Long id;

	@Column(name = "numberRoom", nullable = false)
	@Size(min = 1, max = 200)
	int numberRoom;

	@Column(name = "type", nullable = false)
	@Size(min = 1, max = 200)
	String type;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, orphanRemoval = true)
	List<Sensor> sensors = new ArrayList<Sensor>();

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, orphanRemoval = true)
	@Size(min = 1, max = 200)
	List<Actuator> actuaotors =new ArrayList<Actuator>();
=======
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "numberRoom", nullable = false)
	private int numberRoom;

	@Column(name = "type", nullable = false)
	@Size(min = 1, max = 200)
	private String type;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, orphanRemoval = true)
	private List<Sensor> sensors = new ArrayList<Sensor>();

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, orphanRemoval = true)
	private List<Actuator> actuators = new ArrayList<Actuator>();

	public Room() {
		super();
	}
	
	public Room(int numberRoom, String type) {
		super();
		this.numberRoom = numberRoom;
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
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a

	public int getNumberRoom() {
		return numberRoom;
	}

	public void setNumberRoom(int numberRoom) {
		this.numberRoom = numberRoom;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

<<<<<<< HEAD
	public List<Actuator> getActuaotors() {
		return actuaotors;
	}

	public void setActuaotors(List<Actuator> actuaotors) {
		this.actuaotors = actuaotors;
	}

	public Room(int numberRoom, String type, List<Sensor> sensors, List<Actuator> actuaotors) {
		super();
		this.numberRoom = numberRoom;
		this.type = type;
		this.sensors = sensors;
		this.actuaotors = actuaotors;
	}

	public Room(String type, List<Sensor> sensors, List<Actuator> actuaotors) {
		super();
		this.type = type;
		this.sensors = sensors;
		this.actuaotors = actuaotors;
	}

	public Room(List<Sensor> sensors, List<Actuator> actuaotors) {
		super();
		this.sensors = sensors;
		this.actuaotors = actuaotors;
	}

	public Room(List<Actuator> actuaotors) {
		super();
		this.actuaotors = actuaotors;
	}

	public Room() {
		super();
	}

=======
	public Long getId() {
		return id;
	}

	public List<Actuator> getActuators() {
		return actuators;
	}

	public void setActuators(List<Actuator> actuators) {
		this.actuators = actuators;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", numberRoom=" + numberRoom + ", type=" + type + ", sensors=" + sensors
				+ ", actuators=" + actuators + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + numberRoom;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numberRoom != other.numberRoom)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	
	

	
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
}

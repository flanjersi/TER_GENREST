package fr.amu.terGENREST.entities.projectSpecifications;


import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
	List<Sensor> sensors;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, orphanRemoval = true)
	@Size(min = 1, max = 200)
	List<Actuator> actuaotors;

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

}

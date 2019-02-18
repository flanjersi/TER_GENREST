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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Room(int numberRoom, String type) {
		super();
		this.numberRoom = numberRoom;
		this.type = type;
	}

	public List<Actuator> getActuators() {
		return actuators;
	}

	public void setActuators(List<Actuator> actuators) {
		this.actuators = actuators;
	}

	

	
}

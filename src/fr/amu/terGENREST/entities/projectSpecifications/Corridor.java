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

/**
 * 
 * A Corridor represented by an id ,a List<Sensor> of sensors, a List<Actuator>
 * of actuaotors
 * 
 * @author tsila
 *
 */

@Entity
@Table(name = "Corridor")
@NamedQueries({ @NamedQuery(name = "Corridor.findAllCorridor", query = "SELECT c FROM Corridor c"), })
public class Corridor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Column(name = "numberCorridor", nullable = false)
	private int numberCorridor;

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

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	public List<Actuator> getActuaotors() {
		return actuators;
	}

	public void setActuaotors(List<Actuator> actuaotors) {
		this.actuators = actuaotors;
	}

	public Corridor(List<Sensor> sensors, List<Actuator> actuaotors) {
		super();
		this.sensors = sensors;
		this.actuators = actuaotors;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Corridor() {
		super();
	}

	public Corridor(List<Sensor> sensors) {
		super();
		this.sensors = sensors;
	}

	public int getNumberCorridor() {
		return numberCorridor;
	}

	public void setNumberCorridor(int numberCorridor) {
		this.numberCorridor = numberCorridor;
	}

	public List<Actuator> getActuators() {
		return actuators;
	}

	public void setActuators(List<Actuator> actuators) {
		this.actuators = actuators;
	}

	public Corridor(int numberCorridor) {
		super();
		this.numberCorridor = numberCorridor;
	}

	
}

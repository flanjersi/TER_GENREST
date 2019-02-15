package fr.amu.terGENREST.entities.projectSpecifications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

/**
 * 
 * A Corridor represented by an id ,a List<Sensor> of sensors, a List<Actuator>
 * of actuaotors
 * 
 * @author tsila
 *
 */
public class Corridor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	Long id;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, orphanRemoval = true)
	List<Sensor> sensors = new ArrayList<Sensor>();

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, orphanRemoval = true)
	@Size(min = 1, max = 200)
	List<Actuator> actuaotors = new ArrayList<Actuator>();

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

	public Corridor(List<Sensor> sensors, List<Actuator> actuaotors) {
		super();
		this.sensors = sensors;
		this.actuaotors = actuaotors;
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

	
	
	
}

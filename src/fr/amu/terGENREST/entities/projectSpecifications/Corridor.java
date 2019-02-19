package fr.amu.terGENREST.entities.projectSpecifications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
<<<<<<< HEAD
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
=======
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
<<<<<<< HEAD
import javax.validation.constraints.Size;
=======
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a

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
<<<<<<< HEAD
	@GeneratedValue
	Long id;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, orphanRemoval = true)
	List<Sensor> sensors = new ArrayList<Sensor>();

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, orphanRemoval = true)
	@Size(min = 1, max = 200)
	List<Actuator> actuators = new ArrayList<Actuator>();

=======
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


	public Corridor() {
		super();
	}
	
	public Corridor(int numberCorridor) {
		super();
		this.numberCorridor = numberCorridor;
	}
	
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a
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

<<<<<<< HEAD
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

}
=======

	public Long getId() {
		return id;
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

	@Override
	public String toString() {
		return "Corridor [id=" + id + ", numberCorridor=" + numberCorridor + ", sensors=" + sensors + ", actuators="
				+ actuators + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + numberCorridor;
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
		Corridor other = (Corridor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numberCorridor != other.numberCorridor)
			return false;
		return true;
	}


	
	
}
>>>>>>> bf2e63f2a54a766ff5904a4e1e577f5167caa11a

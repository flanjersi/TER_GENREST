package fr.amu.terGENREST.entities.environmentTechnical;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Table(name = "OperatingSystem")
@Entity
@NamedQueries ({
	@NamedQuery(name = "OperatingSystem.findAllOperatingSystems", query = "SELECT op FROM OperatingSystem op"),
	@NamedQuery(name = "OperatingSystem.findByName", query = "SELECT op FROM OperatingSystem op WHERE name = :name" ),
	@NamedQuery(name = "OperatingSystem.findByPathFolder", query = "SELECT op FROM OperatingSystem op WHERE nameFolder = :nameFolder" )
	
})
public class OperatingSystem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2636092421781516359L;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Basic(optional = false) @Size(min = 1, max = 50)
	@Column(nullable = false, length = 50, unique = true)
	private String name;
	
	/**
	 * Name folder where scripts are store
	 */
	@Basic(optional = false) @Size(min = 1, max = 200)
	@Column(nullable = false, length = 200, unique = true)
	private String nameFolder;
	
	public OperatingSystem() {
		
	}
	
	public OperatingSystem(String name, String namefolder) {
		this.name = name;
		this.nameFolder = namefolder;
	}

	public long getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameFolder() {
		return nameFolder;
	}

	public void setNameFolder(String nameFolder) {
		this.nameFolder = nameFolder;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		OperatingSystem other = (OperatingSystem) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OperatingSystem [id=" + id + ", name=" + name + ", nameFolder=" + nameFolder + "]";
	}
}

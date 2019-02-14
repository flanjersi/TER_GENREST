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
	@NamedQuery(name="OperatingSystem.findAllOperatingSystems", query = "SELECT op FROM OperatingSystem op")
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
	@Column(nullable = false, length = 50)
	private String name;
	
	/**
	 * Name folder where scripts are store
	 */
	@Basic(optional = false) @Size(min = 1, max = 200)
	@Column(nullable = false, length = 200)
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

	public void setId(long id) {
		this.id = id;
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
	public String toString() {
		return "OperatingSystem [id=" + id + ", name=" + name + ", nameFolder=" + nameFolder + "]";
	}
}

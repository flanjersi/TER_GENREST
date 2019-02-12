package fr.amu.terGENREST.entities.environmentTechnical;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

public class OperatingSystem {

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Basic(optional = false) @Size(min = 1, max = 50)
	@Column(nullable = false, length = 50)
	private String name;
	
	/**
	 * Name folder where scripts are storage
	 */
	@Basic(optional = false) @Size(min = 1, max = 200)
	@Column(nullable = false, length = 200)
	private String nameFolder;
	
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

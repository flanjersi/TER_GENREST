package fr.amu.terGENREST.entities.environmentTechnical;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

@Entity
@Table
public class Configuration implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6321329799125743622L;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Basic(optional = false) @Size(min = 1, max = 50)
	@Column(nullable = false, length = 50, unique = true)
	private String name;
	
	@Basic(optional = false) @Size(min = 1, max = 2000)
	@Column(nullable = false, length = 2000)
	private String description;
	
	/**
	 * Path folder where all of templates are store
	 */
	@Basic(optional = false) @Size(min = 1, max = 200)
	@Column(nullable = false, length = 200, unique = true)
	private String pathFolder;
	
	public Configuration() {
		
	}
	
	public Configuration(String name, String description, String pathFolder) {
		this.pathFolder = pathFolder;
		this.description = description;
		this.name = name;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPathFolder() {
		return pathFolder;
	}

	public void setPathFolder(String pathFolder) {
		this.pathFolder = pathFolder;
	}

	@Override
	public String toString() {
		return "Configuration [id=" + id + ", name=" + name + ", description=" + description + ", pathFolder=" + pathFolder + "]";
	}
}

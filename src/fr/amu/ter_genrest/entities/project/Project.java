package fr.amu.ter_genrest.entities.project; 

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import fr.amu.ter_genrest.entities.project_specifications.Building; 

/**
 * 
 * @author Youcef and Mohamed
 * 
 * Project hava 0 or many building 
 *
 */

@NamedQueries({ 
	@NamedQuery( 
			name="Project.findAll",
			query="SELECT p from Project p"),
	@NamedQuery( 
			name="Project.findByName",
			query="SELECT p from Project p where p.projectName = :projectName")
}) 

@Entity
@Table(name = "Project") 
public class Project implements Serializable{ 

	private static final long serialVersionUID = 1L; 
 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id; 

	@Basic(optional = false) 
	@Column(name = "projectName", nullable = false,length=200) 
	@Size(min = 1, max = 200) 
	private String projectName; 

	@Basic(optional = false) 
	@Column(name = "domaine", nullable = false, length=200) 
	@Size(min = 1, max = 200) 
	private String domaine;
	
	@JsonValue
	@Basic(optional = false) 
	@Column(name = "creation_Date", nullable = false) 
	private LocalDateTime  creationDate; 
	
	@JsonValue
	@Basic(optional = true) 
	@Column(name = "change_Date", nullable = true) 
	private LocalDateTime  changeDate; 
	
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE, CascadeType.MERGE,CascadeType.PERSIST }, orphanRemoval=true)
	private Set<Building> buildings = new HashSet<>();

	public Project() {
		
	}
	
	public Project(String projectName, String domaine, LocalDateTime creationDate, LocalDateTime changeDate) {
		this.projectName = projectName;
		this.domaine = domaine;
		this.creationDate = creationDate;
		this.changeDate = changeDate;
	}

	public Long getId() {
		return id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getDomaine() {
		return domaine;
	}

	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(LocalDateTime changeDate) {
		this.changeDate = changeDate;
	}
	
	@Override
	public String toString() {
		return "Project [id=" + id + ", projectName=" + projectName + ", domaine=" + domaine + ", creationDate="
				+ creationDate + ", changeDate=" + changeDate + "]";
	}
	
//--------------------------------------- Building ------------------------------------ // 



	public Set<Building>  getBuilding() {
		return buildings;
	}

	public void setBuilding(Set<Building> buildings) {
		this.buildings.clear();
		this.buildings.addAll(buildings);
	}

	public void addBuilding(Building building) {
		this.buildings.add(building);
	}

	public void removeBuildings(Building building) {
		if(this.buildings.size() == 1 && this.buildings.removeIf(p -> p.getId().equals(building.getId()))) {
			this.buildings.clear();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Project other = (Project) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}


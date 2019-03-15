package fr.amu.ter_genrest.entities.project; 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
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

import fr.amu.ter_genrest.entities.project_specifications.Building; 

/**
 * 
 * @author Youcef 
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

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE, CascadeType.MERGE,CascadeType.PERSIST }, orphanRemoval=true)
	private List<Building> buildings = new ArrayList<>();

	public Project() {
		
	}
	
	public Project(String projectName) {
		this.projectName = projectName;
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
	
//--------------------------------------- Building ------------------------------------ // 

	public List<Building>  getBuilding() {
		return buildings;
	}

	public void setBuilding(List<Building> buildings) {
		this.buildings.clear();
		this.buildings.addAll(buildings);
	}

	public void addBuilding(Building building) {
		if(this.buildings == null) {
			this.buildings = new ArrayList<>();
		}		
		this.buildings.add(building);
	}

	public void removeBuildings(Building building) {
		this.buildings.remove(building);
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", ProjectName=" + projectName + "]";
	}
}
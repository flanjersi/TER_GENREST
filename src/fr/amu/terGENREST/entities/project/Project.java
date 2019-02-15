package fr.amu.terGENREST.entities.project; 

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size; 

@NamedQueries({ 
	@NamedQuery( name="Project.findAll",query="SELECT p from Project p")
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

	//@OneToMany(cascade = { CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST})
	//private List<Building> buildings = new ArrayList<>();

 
	public Project() {
		
	}
	
	public Project(String projectName) {
		super();
		this.projectName = projectName;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


//	public List<Building>  getBuilding() {
//		return buildings;
//	}
//
//
//	public void setBuilding(List<Building> buildings) {
//		this.buildings = buildings;
//	}

//	public void addBuilding(Building building) {
//		if(this.buildings == null) {
//			this.buildings = new ArrayList<>();
//		}
//		
//		this.buildings.add(building);
//	}
//	
//	
//	public void removeBuildings(Building building) {
//		this.buildings.remove(building);
//	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", ProjectName=" + projectName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
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
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		return true;
	}
	
	


}
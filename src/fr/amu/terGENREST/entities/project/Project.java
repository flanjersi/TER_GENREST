package fr.amu.terGENREST.entities.project; 

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

@NamedQueries({ 
	@NamedQuery( name="Project.findAll",query="SELECT p from Project p")
}) 

@Entity
@Table(name = "Project") 
public class Project implements Serializable{ 


	private static final long serialVersionUID = 1L; 

	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id; 

	@Basic(optional = false) 
	@Column(name = "projectName", nullable = false,length=200) 
	@Size(min = 1, max = 200) 
	private String projectName; 

	//@OneToMany(mappedBy="projectSpecifications", fetch=FetchType.LAZY, cascade = { CascadeType.REMOVE}, orphanRemoval=true)
	//private List<ProjectSpecifications> projectSpecifications = new ArrayList<>();

 
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


//	public List<ProjectSpecifications> getProjectSpecifications() {
//		return projectSpecifications;
//	}
//
//
//	public void setProjectSpecifications(List<ProjectSpecifications> projectSpecifications) {
//		this.projectSpecifications = projectSpecifications;
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
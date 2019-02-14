package fr.amu.terGENREST.entities.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

import org.apache.bval.constraints.Email;

//import fr.amu.terGENREST.entities.project.Project;


@NamedQueries({
    @NamedQuery(
        name="User.findAll",
        query="select u from User u"),
    @NamedQuery(
        name="User.Authentication",
        query="SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
})
@Entity
@Table(name = "Users")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name = "first_Name", nullable = false)
	@Size(min = 1, max = 200)
	private String firstName;

	@Column(name = "last_Name", nullable = false)
	@Size(min = 1, max = 200)
	private String lastName;
	
	@Column(name = "email",  nullable = false, unique = true)
	@Email(message = "email is not valid")
	@Size(min=1, max = 200)
	private String email;
	
	@Column(name = "password",  nullable = false)
	@Size(min=1, max = 200)
	private String password;
	
//	@OneToMany(fetch=FetchType.LAZY, cascade = { CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval=true)
//	private List<Project> projects;
	
	public User() {	}
	
	public User(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
//	public List<Project> getProjects() {
//		return projects;
//	}
//
//	public void setProjects(List<Project> projects) {
//		this.projects = projects;
//	}
//
//	public void addProject(Project project) {
//		if(this.projects == null) {
//			this.projects = new ArrayList<>();
//		}
//		
//		this.projects.add(project);
//	}
//	
//	
//	public void removeProject(Project project) {
//		this.projects.remove(project);
//	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + "]";
	}	
	
	
}
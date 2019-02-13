package fr.amu.terGENREST.entities.user;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.bval.constraints.Email;


@NamedQueries({
    @NamedQuery(
        name="User.findAll",
        query="select u from User u"),
    @NamedQuery(
        name="User.Authentification",
        query="SELECT u FROM User u WHERE u.email=:email AND u.password=:password"), 
})
@Entity
@Table(name = "Users")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "first_Name", nullable = false)
	@Size(min = 1, max = 200)
	private String firstName;

	@Column(name = "last_Name", nullable = false)
	@Size(min = 1, max = 200)
	private String lastName;
	
	@Column(name = "email",  nullable = false)
	@Email(message = "email is not valid")
	@Size(min=1, max = 200)
	private String email;
	
	@Column(name = "password",  nullable = false)
	@Size(min=1, max = 200)
	private String password;
	
//	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade = { CascadeType.REMOVE}, orphanRemoval=true)
//	private Project project;
	
	public User() {	}
	
	public User(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	
	public User(Long id, String firstName, String lastName, String email, String password) {
		super();
		this.id =  id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
//	public Project getProject() {
//		return project;
//	}
//
//	public void setProject(Project project) {
//		this.project = project;
//	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + "]";
	}
	 
	
}

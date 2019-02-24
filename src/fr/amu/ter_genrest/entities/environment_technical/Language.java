package fr.amu.ter_genrest.entities.environment_technical;

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


@NamedQueries ({
	@NamedQuery(name = "Language.findAllLanguages", query = "FROM Language"),
	@NamedQuery(name = "Language.findByName", query = "FROM Language WHERE name = :name")

})

@Entity
@Table(name = "Language")
public class Language implements Serializable{

	private static final long serialVersionUID = 1437856201827260331L;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Basic(optional = false) @Size(min = 1, max = 50)
	@Column(nullable = false, length = 50, unique = true)
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,  orphanRemoval = true)
	private List<Configuration> configurationsAvailable = new ArrayList<Configuration>();
	
	public Language() {	
	}
	
	public Language(String name) {
		this.name = name;
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

	public List<Configuration> getConfigurationsAvailable() {
		return configurationsAvailable;
	}

	public void setConfigurationsAvailable(List<Configuration> configurationsAvailable) {
		this.configurationsAvailable = configurationsAvailable;
	}

	public void addConfiguration(Configuration configuration) {
		configurationsAvailable.add(configuration);
	}
	
	public void removeConfiguration(Configuration configuration) {
		configurationsAvailable.remove(configuration);
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
		Language other = (Language) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Language [id=" + id + ", name=" + name + ", configurationsAvailable=" + configurationsAvailable + "]";
	}
}

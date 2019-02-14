package fr.amu.terGENREST.entities.environmentTechnical;

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

@Entity
@Table(name = "Language")
@NamedQueries ({
	@NamedQuery(name = "Language.findAllLanguages", query = "FROM Language")
})
public class Language implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1437856201827260331L;

	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Basic(optional = false) @Size(min = 1, max = 50)
	@Column(nullable = false, length = 50)
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},  orphanRemoval = true)
	private List<Configuration> configurationsAvailable = new ArrayList<Configuration>();
	
	public Language() {
		
	}
	
	public Language(String name) {
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
	public String toString() {
		return "Language [id=" + id + ", name=" + name + ", configurationsAvailable=" + configurationsAvailable + "]";
	}
}
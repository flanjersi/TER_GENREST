package fr.amu.terGENREST.entities.projectSpecifications;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Table(name = "Apartment")
@Entity
public class Apartment {
	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Basic(optional = false) @Size(min = 1, max = 10)
	@Column(nullable = false)
	private int apartmentNumber;
	
//	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},  orphanRemoval = true)
//	private List<Room> appartmentRoom = new ArrayList<Room>();
}

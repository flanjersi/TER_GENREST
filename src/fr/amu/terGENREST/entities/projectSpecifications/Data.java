package fr.amu.terGENREST.entities.projectSpecifications;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/***
 * Data: represented by id (auto généré)dateTime (not null)value (not null)
 * 
 * 
 * @author tsila
 *
 */

@NamedQueries({ @NamedQuery(name = "Data.findAll", query = "select a from Data a"), })
@Entity
@Table(name = "Data")
public class Data implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "dateTime", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dateTime;

	@Column(name = "value", nullable = false)
	@Size(min = 1, max = 200)
	private String value;

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Data(Date dateTime, String value) {
		super();
		this.dateTime = dateTime;
		this.value = value;
	}

	public Data() {
		super();
	}

	
}

package fr.amu.terGENREST.entities.projectSpecifications;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	@GeneratedValue
	Long id;

	@Basic()
	@Temporal(TemporalType.DATE)
	Date dateTime;

	@Basic
	String value;

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

	public Data(String value) {
		super();
		this.value = value;
	}

	public Data(Date dateTime) {
		super();
		this.dateTime = dateTime;
	}

	public Data() {
		super();
	}

}
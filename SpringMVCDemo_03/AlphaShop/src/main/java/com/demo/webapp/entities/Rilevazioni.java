package com.demo.webapp.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "TRASMISSIONI")
public class Rilevazioni implements Serializable
{
 
	private static final long serialVersionUID = 7650154558839481243L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "Data")
	private Date data;
	
	@Basic
	private String idTerminale;
	
	@Basic
	private String barcode;
	
	@Transient
	private String descrizione;
	
	@Basic
	private String qta;
	
	public Rilevazioni()
	{}
	
	public Rilevazioni(Date Data, String IdTerminale, String Barcode, String Qta)
	{
		this.data = Data;
		this.idTerminale = IdTerminale;
		this.barcode = Barcode;
		this.qta = Qta;
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getIdTerminale() {
		return idTerminale;
	}

	public void setIdTerminale(String idTerminale) {
		this.idTerminale = idTerminale;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getQta() {
		return qta;
	}

	public void setQta(String qta) {
		this.qta = qta;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	
	

}

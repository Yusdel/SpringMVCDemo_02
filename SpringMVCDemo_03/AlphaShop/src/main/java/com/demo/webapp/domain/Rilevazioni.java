package com.demo.webapp.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Yusdel Morales
 * To process each line of the uploaded file. 
 * This class represents the single row model.
 * 
 * TODO Reading and Processing a file 
 */
public class Rilevazioni implements Serializable
{
 
	private static final long serialVersionUID = 7650154558839481243L;
	
	private String data;
	private String idTerminale;
	private String barcode;
	private String descrizione;
	private String qta;
	
	public Rilevazioni()
	{}
	
	public Rilevazioni(String Data, String IdTerminale, String Barcode, String Qta)
	{
		this.data = Data;
		this.idTerminale = IdTerminale;
		this.barcode = Barcode;
		this.qta = Qta;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
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

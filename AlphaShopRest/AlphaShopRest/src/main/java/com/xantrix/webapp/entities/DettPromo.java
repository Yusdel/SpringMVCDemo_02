package com.xantrix.webapp.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
 

@Entity
@Table(name = "DETTPROMO")
@Data
public class DettPromo implements Serializable
{
	private static final long serialVersionUID = 7444232363326102441L;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "RIGA")
	private int riga;
	
	@Column(name = "CODART")
	private String codart;
	
	@Column(name = "CODFID")
	private String codfid;
		
	@Column(name = "INIZIO")
	@Temporal(TemporalType.DATE)
	private Date inizio;
	
	@Column(name = "FINE")
	@Temporal(TemporalType.DATE)
	private Date fine;
	
	@Column(name = "OGGETTO")
	private String oggetto;
	
	@Column(name = "ISFID")
	private String isfid;
	
	@ManyToOne
	@JoinColumn(name = "IDPROMO", referencedColumnName = "idPromo")
	@JsonBackReference 
	private Promo promo;
	
	@ManyToOne
	@EqualsAndHashCode.Exclude
	@JoinColumn(name = "IDTIPOPROMO", referencedColumnName = "idTipoPromo")
	private TipoPromo tipoPromo;
	
	/*
	public DettPromo() {}
	
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public int getRiga()
	{
		return riga;
	}

	public void setRiga(int riga)
	{
		this.riga = riga;
	}

	public String getCodart()
	{
		return codart;
	}

	public void setCodart(String codart)
	{
		this.codart = codart;
	}

	public String getCodfid()
	{
		return codfid;
	}

	public void setCodfid(String codfid)
	{
		this.codfid = codfid;
	}

	public Date getInizio()
	{
		return inizio;
	}

	public void setInizio(Date inizio)
	{
		this.inizio = inizio;
	}

	public Date getFine()
	{
		return fine;
	}

	public void setFine(Date fine)
	{
		this.fine = fine;
	}

	public String getOggetto()
	{
		return oggetto;
	}

	public void setOggetto(String oggetto)
	{
		this.oggetto = oggetto;
	}
	
	public String getIsfid()
	{
		return isfid;
	}

	public void setIsfid(String isfid)
	{
		this.isfid = isfid;
	}

	public Promo getPromo()
	{
		return promo;
	}

	public void setPromo(Promo promo)
	{
		this.promo = promo;
	}

	public TipoPromo getTipoPromo()
	{
		return tipoPromo;
	}

	public void setTipoPromo(TipoPromo tipoPromo)
	{
		this.tipoPromo = tipoPromo;
	}
	*/

}

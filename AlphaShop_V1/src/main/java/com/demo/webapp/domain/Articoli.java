package com.demo.webapp.domain;

import java.io.Serializable;
import java.util.Date;

/* For domain-type classes, serialization must always be implemented */
public class Articoli implements Serializable{
	
	private static final long serialVersionUID = -411811183648559893L;
	
	private String codArt;
	private String descrizione;	
	private String um;
	private String codStat;
	private int pzCart;
	private double pesoNetto;
	private int idIva;
	private String idStatoArt;
	private Date dataCreaz;
	private int idFamAss;
	
	/* Anonymous Constructor*/
	public Articoli()
	{
	}

	public String getCodArt() {
		return codArt;
	}

	public void setCodArt(String codArt) {
		this.codArt = codArt;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getUm() {
		return um;
	}

	public void setUm(String um) {
		this.um = um;
	}

	public String getCodStat() {
		return codStat;
	}

	public void setCodStat(String codStat) {
		this.codStat = codStat;
	}

	public int getPzCart() {
		return pzCart;
	}

	public void setPzCart(int pzCart) {
		this.pzCart = pzCart;
	}

	public double getPesoNetto() {
		return pesoNetto;
	}

	public void setPesoNetto(double pesoNetto) {
		this.pesoNetto = pesoNetto;
	}

	public int getIdIva() {
		return idIva;
	}

	public void setIdIva(int idIva) {
		this.idIva = idIva;
	}

	public String getIdStatoArt() {
		return idStatoArt;
	}

	public void setIdStatoArt(String idStatoArt) {
		this.idStatoArt = idStatoArt;
	}

	public Date getDataCreaz() {
		return dataCreaz;
	}

	public void setDataCreaz(Date dataCreaz) {
		this.dataCreaz = dataCreaz;
	}

	public int getIdFamAss() {
		return idFamAss;
	}

	public void setIdFamAss(int idFamAss) {
		this.idFamAss = idFamAss;
	}
}

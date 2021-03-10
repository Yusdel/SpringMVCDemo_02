package com.demo.webapp.domain;

import java.io.Serializable;

public class Movimenti implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String codArt;
	private String desArt;
	private double acquisto;
	private String um;
	private float acquistato;
	private float reso;
	private float venduto;
	private float uscite;
	private float scaduti;
	private float magazzino;
	private float incscaduti;
	
	public Movimenti( ) {}

	public String getCodArt() {
		return codArt;
	}

	public void setCodArt(String codArt) {
		this.codArt = codArt;
	}

	public String getDesArt() {
		return desArt;
	}

	public void setDesArt(String desArt) {
		this.desArt = desArt;
	}

	public double getAcquisto() {
		return acquisto;
	}

	public void setAcquisto(double acquisto) {
		this.acquisto = acquisto;
	}

	public float getReso() {
		return reso;
	}

	public void setReso(float reso) {
		this.reso = reso;
	}

	public float getVenduto() {
		return venduto;
	}

	public void setVenduto(float venduto) {
		this.venduto = venduto;
	}

	public float getUscite() {
		return uscite;
	}

	public void setUscite(float uscite) {
		this.uscite = uscite;
	}

	public float getScaduti() {
		return scaduti;
	}

	public void setScaduti(float scaduti) {
		this.scaduti = scaduti;
	}

	public float getAcquistato() {
		return acquistato;
	}

	public void setAcquistato(float acquistato) {
		this.acquistato = acquistato;
	}

	public String getUm() {
		return um;
	}

	public void setUm(String um) {
		this.um = um;
	}

	public float getMagazzino() {
		return magazzino;
	}

	public void setMagazzino(float magazzino) {
		this.magazzino = magazzino;
	}

	public float getIncscaduti() {
		return incscaduti;
	}

	public void setIncscaduti(float incscaduti) {
		this.incscaduti = incscaduti;
	}
	
	
	
}

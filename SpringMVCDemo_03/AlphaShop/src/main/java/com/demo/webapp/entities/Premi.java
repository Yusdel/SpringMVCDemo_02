package com.demo.webapp.entities;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/*
 * TODO New Notation : AttributeOverride - Basic
 * TODO New Notation : Large Object (LOB - like images)
 * 
 * @AttributeOverride = Use field "codFidelity" as field "Fidelity"
 * ( This because in the table Premi the name of field codFidelity is Fidelity )
 * 
 * @Basic = Instruct Hibernate to perform basic mappings
 * ( Similar to @Column notation | (optional = true) mean that the null value is possible "nullable field")
 * 
 * @Lob = field is an Large Object ( example image, sound ... )
 */

@Entity
@Table(name = "Premi")
@AttributeOverride(name = "codFidelity",column = @Column(name="Fidelity"))
public class Premi  extends AbstractEntityClienti implements Serializable
{
	private static final long serialVersionUID = 7469929863994780647L;

	@Basic(optional = true)
	private int qtaPremi;
	
	@Basic(optional = true)
	private double valPremi;
	
	// TODO New Notation : Large Object (LOB - like images)
	@Basic(fetch = FetchType.LAZY)
	@Lob
	@Column(name="Img")
	private byte[] immagine;
	
	// TODO New Notation : Embedded Objects
	@Embedded 
	private InfoPremi infoPremi;
	
	@OneToOne(mappedBy = "premi")
	private Clienti cliente;
	
	public Premi()
	{
	}

	public int getQtaPremi() {
		return qtaPremi;
	}

	public void setQtaPremi(int qtaPremi) {
		this.qtaPremi = qtaPremi;
	}

	public double getValPremi() {
		return valPremi;
	}

	public void setValPremi(double valPremi) {
		this.valPremi = valPremi;
	}

	public Clienti getCliente() {
		return cliente;
	}

	public void setCliente(Clienti cliente) {
		this.cliente = cliente;
	}

	public byte[] getImmagine() {
		return immagine;
	}

	public void setImmagine(byte[] immagine) {
		this.immagine = immagine;
	}

	public InfoPremi getInfoPremi() {
		return infoPremi;
	}

	public void setInfoPremi(InfoPremi infoPremi) {
		this.infoPremi = infoPremi;
	}
	
	
	
	
}

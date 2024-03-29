package com.demo.webapp.entities;

import java.io.Serializable;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * STEP B-Using: TODO Hibernate and JPA Framework
 * @author Yusdel Morales
 *
 */

@Entity
@Table(name = "CARDS")
public class Cards extends AbstractEntityClienti implements Serializable
{
	private static final long serialVersionUID = -3751231307546162427L;
	
	@Column(name = "BOLLINI")
	private Integer bollini;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ULTIMASPESA")
	private Date ultimaSpesa;
	
	@Column(name = "OBSOLETO")
	private String obsoleto;
	
	@OneToOne(mappedBy = "card") // Intersection point mapped by the "card" field (client table or mapped IN ..)
	private Clienti cliente;
 	
	public Cards() {}
	
	public Cards(String CodFid, Integer Bollini)
	{
		super.codFidelity = CodFid; // TODO Entity abstract superclass
		this.bollini = Bollini;
	}

	public Integer getBollini()
	{
		return (bollini == null) ? 0 : bollini;
	}

	public void setBollini(Integer bollini)
	{
		this.bollini = bollini;
	}

	public Date getUltimaSpesa()
	{
		return ultimaSpesa;
	}

	public void setUltimaSpesa(Date ultimaSpesa)
	{
		this.ultimaSpesa = ultimaSpesa;
	}

	public String getObsoleto()
	{
		return obsoleto;
	}

	public void setObsoleto(String obsoleto)
	{
		this.obsoleto = obsoleto;
	}

	public Clienti getCliente() {
		return cliente;
	}

	public void setCliente(Clienti cliente) {
		this.cliente = cliente;
	}
	
	
	
}

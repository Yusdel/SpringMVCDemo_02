package com.demo.webapp.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * STEP B-Using: TODO Hibernate and JPA Framework
 * @author Yusdel Morales
 *
 */

@Entity
@Table(name = "PROFILI")
public class Profili  implements Serializable
{

	private static final long serialVersionUID = -298809320824675645L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // Specify that this value is auto-generated as a type IDENTITY
	private long id;
	
	@Column(name = "TIPO")
	private String tipo;
	
	@ManyToOne
	@JoinColumn(name = "CODFIDELITY", referencedColumnName = "codFidelity") // Foreign key (or Cardinality)
	private Utenti utente;
	
	public Profili() {}
	
	public Profili(String Tipo, Utenti Utente)
	{
		this.tipo = Tipo;
		this.utente = Utente;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Utenti getUtente() {
		return utente;
	}

	public void setUtente(Utenti utente) {
		this.utente = utente;
	}
	
	
	

}

package com.demo.webapp.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.management.loading.PrivateClassLoader;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

/*
 * If we create an entity of a table that does not exist in the db,
 * the Hibernate will provide for its generation.
 * 
 * STEP B-Using: TODO Hibernate and JPA Framework
 * 
 * TODO New Notation :  Mapping Enumerator
 * TODO New Notation : Transient
 * @Transient = field not in the table but required in the class
 */

@Entity
@Table(name = "CLIENTI") //to identify reference table (it's only needed if the class name is not the same as the table name in the DB)
public class Clienti extends AbstractEntityClienti implements Serializable
{
	private static final long serialVersionUID = 3102487555480706992L;
	
	@Column(name = "NOME")
	@Size(min=2, max=50, message = "{Size.Clienti.nome.validation}")
	private String nome;
	
	@Column(name = "COGNOME")
	@Size(min=2, max=60, message = "{Size.Clienti.cognome.validation}")
	private String cognome;
	
	@Column(name = "INDIRIZZO")
	@Size(min=10, max=80, message = "{Size.Clienti.indirizzo.validation}")
	private String indirizzo;
	
	@Column(name = "COMUNE")
	@Size(min=2, max=50, message = "{Size.Clienti.comune.validation}")
	private String comune;
	
	@Transient
	private String nominativo;
	
	@Column(name = "CAP")
	private String cap;
	
	@Column(name = "PROV")
	private String prov;
	
	@Column(name = "TELEFONO")
	private String telefono;
	
	@Column(name = "MAIL")
	private String mail;
	
	@Column(name = "STATO")
	@Enumerated(EnumType.ORDINAL) // TODO New Notation :  Mapping Enumerator
	private Stato stato;
	
	//@DateTimeFormat(pattern = "yyyy-mm-dd")
	@Temporal(TemporalType.DATE) // Only date (dd-mm-yyyy) for datetime use .TIME
	@Column(name = "DATACREAZ")
	private Date dataCreaz;
	
	/*
	 * Note. CARDS table can't modified by Web Application because is a statistic table (populated by Automatic System ETL)
	 * so the relation with CLienti have the particular annotation @
	 * 
	 * @FetchType
	 * .LAZY = upload data only when needed
	 * .EAGER = upload always data
	 * 
	 * @PrimaryKeyJoinColumn = join through primary key
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn 
	private Cards card;
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Premi premi;
	
	// TODO generated primary key
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cliente")
	private Set<Coupons> coupons = new HashSet<>();
	
	/*
	 * mappedBy = field when is mapped
	 * cascade = Operations we can perform on the "utenti" table. ( CRUD )
	 * orphanRemoval = for integrity referenced. 
	 */
	@OneToOne(mappedBy = "clienti", cascade = CascadeType.ALL, orphanRemoval = true)
	private Utenti utenti;
	
	public Clienti() { } // Anonymous Constructor

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Date getDataCreaz() {
		return dataCreaz;
	}

	public void setDataCreaz(Date date) {
		this.dataCreaz = date;
	}
	
	
	public Cards getCard() {
		return card;
	}

	public void setCard(Cards card) {
		this.card = card;
	}

	public Utenti getUtenti() {
		return utenti;
	}

	public void setUtenti(Utenti utenti) {
		this.utenti = utenti;
	}

	public String getNominativo() {
		return this.nome + " " + this.cognome;
	}

	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
	}

	// TODO New Notation :  Mapping Enumerator
	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}

	public Set<Coupons> getCoupons() {
		return coupons;
	}

	public void setCoupons(Set<Coupons> coupons) {
		this.coupons = coupons;
	}

}

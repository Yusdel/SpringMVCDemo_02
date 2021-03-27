package com.xantrix.webapp.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "PROMO")
@Data
public class Promo implements Serializable
{
	private static final long serialVersionUID = -5905631309290304849L;
	
	@Id
	@Column(name = "IDPROMO")
	private String idPromo;
	
	@Column(name = "ANNO")
	private int anno;
	
	@Column(name = "CODICE")
	private String codice;
	
	@Column(name = "DESCRIZIONE")
	private String descrizione;
	
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,  mappedBy = "promo", orphanRemoval = true)
	@JsonManagedReference
	private List<DettPromo> dettPromo;
	
	/*
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,  mappedBy = "promo", orphanRemoval = true)
	@JsonManagedReference
	private List<DepRifPromo> depRifPromo;
	*/
	
	
}

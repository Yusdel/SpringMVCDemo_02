package com.xantrix.webapp.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

/*
 * @JsonManagedReference = Required when the application return JSON Data
 * (required @JsonBackReference in the associated/join table/entity )
 */

@Entity
@Table(name = "PROMO")
@Data
public class Promo  implements Serializable
{
	private static final long serialVersionUID = -2077445225617424877L;

	@Id
	@Column(name = "IDPROMO")
	private String idPromo;
	
	@Basic(optional = false) // Field can't be null
	private int anno;
	
	@Basic(optional = false)
	private String codice;
	
	@Basic
	private String descrizione;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,  mappedBy = "promo",  orphanRemoval = true)
	@JsonManagedReference
	private Set<DettPromo> dettPromo = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,  mappedBy = "promo", orphanRemoval = true)
	@JsonManagedReference
	private Set<DepRifPromo> depRifPromo = new HashSet<>();
}

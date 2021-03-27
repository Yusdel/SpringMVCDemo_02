package com.xantrix.webapp.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
 
import lombok.Data;

@Entity
@Table(name = "DEPRIFPROMO")
@Data
public class DepRifPromo  implements Serializable
{
private static final long serialVersionUID = 1436206967746080890L;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "IDDEPOSITO")
	private int idDeposito;
	
	@ManyToOne
	@JoinColumn(name = "IDPROMO", referencedColumnName = "idPromo")
	@JsonBackReference 
	//@JsonIgnore
	private Promo promo;
	
}

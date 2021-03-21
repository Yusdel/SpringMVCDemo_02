package com.demo.webapp.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
 * TODO Entity abstract superclass
 * 
 * @MappedSuperclass = request notation for abstract entity class
 */
@MappedSuperclass
public abstract class AbstractEntityClienti implements Serializable
{
	
	private static final long serialVersionUID = 1071912133624366771L;
	
	@Id // Primary Key ( We cannot have an Entity without a primary key ) 
	@Column(name = "CODFIDELITY")
	@NotNull(message = "{NotNull.Clienti.codFidelity.validation}")
	@Size(min=8, max=20, message = "{Size.Clienti.codFidelity.validation}")
	protected String codFidelity;

	public String getCodFidelity() {
		return codFidelity;
	}

	public void setCodFidelity(String codFidelity) {
		this.codFidelity = codFidelity;
	}
	
	
}

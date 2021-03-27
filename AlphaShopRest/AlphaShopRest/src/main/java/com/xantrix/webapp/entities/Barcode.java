package com.xantrix.webapp.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "BARCODE")
@Data
public class Barcode  implements Serializable
{
	private static final long serialVersionUID = 8682477643109847337L;
	
	@Id
	@Column(name = "BARCODE")
	@NotNull(message = "{NotNull.Barcode.barcode.Validation}")
	@Size(min = 8, max = 13, message = "{Size.Barcode.barcode.Validation}")
	private String barcode;
	
	@Column(name = "IDTIPOART")
	@NotNull(message = "{NotNull.Barcode.idTipoArt.Validation}")
	private String idTipoArt;
	
	@ManyToOne
	@EqualsAndHashCode.Exclude // required by lombok to the @ManyToOne notation
	@JoinColumn(name = "CODART", referencedColumnName = "codArt")
	@JsonBackReference // Jackson End Poin o punto di arrivo, se non usiamo questa annotazione si va in loop
	private Articoli articolo; //Il nome deve essere coerente con parametro MappedBy

}

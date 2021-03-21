package com.demo.webapp.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/*
 * TODO generated primary key
 */
@Entity
@Table(name = "Coupons")
public class Coupons implements Serializable
{
	private static final long serialVersionUID = -2788720560904709897L;
	
	// TODO Generation Type Strategy
	@Id
	//@GeneratedValue(strategy=GenerationType.TABLE)
	/*
	@TableGenerator(
		name="Coupons",
		table="Progressivi",
		pkColumnName="Tipo",
		valueColumnName="Progressivo",
		allocationSize=200
		)
		*/
	@SequenceGenerator(name="Coup_Gen", sequenceName="Test_Seq")
	@GeneratedValue(generator="Coup_Gen")
	//@GeneratedValue(generator="Coupons")
	@Column(name = "Id")
	private long id;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "Data")
	private Date dataCreaz;
	
	@Basic
	private int idDeposito;
	
	@Basic
	private int qta;
	
	@Basic
	private double valore;
	
	@ManyToOne
	@JoinColumn(name = "IdCliente", referencedColumnName = "CODFIDELITY")
	private Clienti cliente;
	
	public Coupons()
	{
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataCreaz() {
		return dataCreaz;
	}

	public void setDataCreaz(Date dataCreaz) {
		this.dataCreaz = dataCreaz;
	}

	public int getIdDeposito() {
		return idDeposito;
	}

	public void setIdDeposito(int idDeposito) {
		this.idDeposito = idDeposito;
	}

	public int getQta() {
		return qta;
	}

	public void setQta(int qta) {
		this.qta = qta;
	}

	public double getValore() {
		return valore;
	}

	public void setValore(double valore) {
		this.valore = valore;
	}

	public Clienti getCliente() {
		return cliente;
	}

	public void setCliente(Clienti cliente) {
		this.cliente = cliente;
	}
	
	
	
	
	
}

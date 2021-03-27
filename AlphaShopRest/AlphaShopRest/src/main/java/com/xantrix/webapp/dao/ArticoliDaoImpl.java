package com.xantrix.webapp.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;
import com.xantrix.webapp.entities.Articoli;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ArticoliDaoImpl  extends AbstractDao<Articoli, String> 
implements ArticoliDao
{

	@Override
	public Articoli SelByCodArt(String CodArt)
	{
		String JPQL = "SELECT a FROM Articoli a WHERE a.codArt = :codart";
		
		Articoli articolo;
		
		try
		{
			 articolo =  (Articoli) entityManager.createQuery(JPQL)
					 .setParameter("codart", CodArt)
					 .getSingleResult();
		}
		catch (NoResultException ex)
		{
			articolo = null;
			log.info(ex.getMessage());
		}
		
		return articolo;
		 
	}

	@Override
	public List<Articoli> SelByDescrizione(String Descrizione)
	{
		/*
		 * CONCAT(a,b,c) = JPQL method 
		 */
		String JPQL = "SELECT a FROM Articoli a WHERE a.descrizione LIKE CONCAT('%',:desart,'%')";
		
		@SuppressWarnings("unchecked")
		List<Articoli> articoli =  entityManager.createQuery(JPQL)
									   .setParameter("desart", Descrizione)
									   .getResultList();
		
		return articoli;
	}

	@Override
	public Articoli SelByEan(String Ean)
	{
		String JPQL = "SELECT a FROM Articoli a JOIN a.barcode b WHERE b.barcode IN (:ean)";
		
		Articoli articolo; 
		
		try
		{
			articolo =  (Articoli) entityManager.createQuery(JPQL)
									 .setParameter("ean", Ean)
									 .getSingleResult();
		}
		catch (NoResultException ex)
		{
			articolo = null;
			log.info(ex.getMessage());
		}
		
		return articolo;
	}

	@Override
	public void Salva(Articoli articolo)
	{
		super.Inserisci(articolo);
	}
	
	@Override
	public void Aggiorna(Articoli Articolo)
	{
		super.Aggiorna(Articolo);
	}

	@Override
	public void Elimina(Articoli Articolo)
	{
		super.Elimina(Articolo);
	}

}

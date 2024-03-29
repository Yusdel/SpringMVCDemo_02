package com.demo.webapp.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import com.demo.webapp.entities.Utenti;

/*
 * STEP B-Using: TODO Hibernate and JPA Framework
 */

@Repository
public class UtentiDaoImpl  extends AbstractDao<Utenti, Integer> 
	implements UtentiDao
{
	
	// TODO Spring Security
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public Utenti SelByIdFidelity(String idFidelity)
	{
		Utenti retVal = new Utenti();
		
		try
		{
			CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Utenti> queryDefinition = queryBuilder.createQuery(Utenti.class);
			
			Root<Utenti> recordset = queryDefinition.from(Utenti.class);
			
			queryDefinition.select(recordset).
							where(queryBuilder.equal(recordset.get("codFidelity"), idFidelity));
			
			retVal =  entityManager.createQuery(queryDefinition).getSingleResult();
			
			return retVal;
		}
		catch(Exception ex)
		{
			return retVal;
		}
	}
	
	@Override
	public void Salva(Utenti utente)
	{
		super.Inserisci(utente);
	}
	
	@Override
	public void Aggiorna(Utenti utente)
	{
		super.Aggiorna(utente);
	}
	
	@Override
	public void Elimina(Utenti utente)
	{
		super.Elimina(utente);
	}

	// TODO Custom Validation if client exist
	@Override
	public Utenti SelByUserId(String UserId) {
		Utenti utente = null;
			
		try
			{
				String JPQL = "SELECT a FROM Utenti a WHERE a.userId = :UserId";
				
				utente = (Utenti) entityManager.createQuery(JPQL)
						.setParameter("UserId", UserId)
						.getSingleResult();
			}
			catch (Exception ex)
			{ }
			
			return utente;
		}

	@Override
	public Utenti SelByUserIdCodFid(String UserId, String CodFid) {
		
		Utenti retVal;
		
		String JPQL = "SELECT a FROM Utenti a WHERE a.userId = :userId AND codFidelity = :codFid";
		
		retVal = (Utenti) entityManager.createQuery(JPQL)
				  .setParameter("userId", UserId)
				  .setParameter("codFid", CodFid)
				  .getSingleResult();	 

		return retVal;
	}

	// TODO Spring Security
	@Override
	public void SalvaAdminUser(String Password) {
		
		String EncodedPwd = passwordEncoder.encode(Password);
		
		String Sql = "EXEC Sp_InsAdminUser '" + EncodedPwd + "'";
		
		entityManager
			.createNativeQuery(Sql)
			.executeUpdate();
	}

	

}

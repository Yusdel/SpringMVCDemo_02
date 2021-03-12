package com.demo.webapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.demo.webapp.domain.Articoli;
import com.demo.webapp.domain.Rilevazioni;

/**
 * 
 * @author Yusdel Morales
 * 
 * TODO Reading and Processing a file 
 */
@Repository
public class RilevazioniRepositoryImpl implements RilevazioniRepository
{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Rilevazioni> SelTrasmByFilter(String Filtro) 
	{
		String Sql = "EXEC [dbo].[Sp_SelDatiTrasm] ?;";
		
		List<Rilevazioni> rilevazioni = jdbcTemplate.query(Sql, new RilevazioniMapper(), Filtro);
		
		return rilevazioni;
	}

	@Override
	public void InsTrasm(Rilevazioni Rilev) 
	{
		String Sql = "EXEC [dbo].[Sp_InsDatiTrasm] '" + 
					Rilev.getData() + "','" + 
					Rilev.getIdTerminale() + "','" + 
					Rilev.getBarcode() + "','" +
					Rilev.getQta()  + "'";
		 
		jdbcTemplate.update(Sql);
		
	}

	@Override
	public void DelTrasm(String IdTerminale) 
	{
		String Sql = "DELETE FROM [dbo].[TRASMISSIONI] WHERE IdTerminale = ?";
		
		jdbcTemplate.update(Sql, IdTerminale);
		
	}

}

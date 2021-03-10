package com.demo.webapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.demo.webapp.domain.Movimenti;

@Repository
public class MovimentiRepositoryImpl implements MovimentiRepository
{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Movimenti SelMovimentiByCodArt(String CodArt) 
	{
		String Sql = "SELECT A.CODART,B.DESCRIZIONE,A.PRZACQ,B.UM,A.ACQUISTATO,A.RESO,A.VENDUTO,A.USCITE,A.SCADUTI, " +
					 "(A.ACQUISTATO - A.RESO - A.VENDUTO - A.USCITE - A.SCADUTI) AS QTAMAG, " + 
					 "(A.SCADUTI / A.ACQUISTATO) AS INCSCAD " +
					 "FROM MOVIMENTI A JOIN ARTICOLI B ON A.CODART = B.CODART " + 
					 "WHERE A.CODART = ? ";
	 	
		Movimenti movimenti = null;
	
		try
		{
			 movimenti = jdbcTemplate.queryForObject(Sql, new MovimentiMapper(), new Object[] {CodArt}); //CodArt
		}
		catch (EmptyResultDataAccessException ex)
		{
			System.out.println(ex.getMessage());
		}
	
		return movimenti;
	}
	
	@Override
	public List<Movimenti> SelMovimentiByFilter(String Filtro) 
	{
		String Sql = "EXEC Sp_SelMovimenti ?";
		
		List<Movimenti> articoli = jdbcTemplate.query(Sql, new MovimentiMapper(), Filtro);
		
		return articoli;
	}

	@Override
	public List<Movimenti> SelArticoliByFilter(String Filtro, String OrderBy, String Tipo) 
	{
		String Sql = "EXEC Sp_SelMovimenti ?,?,?";
		
		List<Movimenti> articoli = jdbcTemplate.query(Sql, new MovimentiMapper(), Filtro,OrderBy,Tipo);
		
		return articoli;
	}

	

}

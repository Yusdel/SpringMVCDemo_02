package com.demo.webapp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.demo.webapp.domain.Ingredienti;

public class IngredientiMapper implements RowMapper<Ingredienti>
{
	@Override
	public Ingredienti mapRow(ResultSet row, int rowNum) throws SQLException
	{
		 Ingredienti ingredienti = new Ingredienti();
		
		 try
		 {
			 ingredienti.setCodArt(row.getString("CODART"));  
			 ingredienti.setInfo(row.getString("INFO").trim());
		 }
		 catch (Exception ex)
		 {
			 System.out.println("Errore in IngredientiMapper.mapRow: " + ex);
		 }
	
		return ingredienti;
	
	}
}

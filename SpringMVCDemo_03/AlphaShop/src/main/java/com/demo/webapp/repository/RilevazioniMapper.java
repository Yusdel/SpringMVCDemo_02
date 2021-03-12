package com.demo.webapp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.demo.webapp.domain.Rilevazioni;

/**
 * 
 * @author Yusdel Morales
 * 
 * TODO Reading and Processing a file 
 */
public class RilevazioniMapper implements RowMapper<Rilevazioni>
{
	public Rilevazioni mapRow(ResultSet row, int rowNum) throws SQLException
	{
		Rilevazioni rilevazione = new Rilevazioni();
		
		try
		{
			rilevazione.setData(row.getString("Data"));
			rilevazione.setIdTerminale(row.getString("IdTerminale"));
			rilevazione.setDescrizione(row.getString("Descrizione"));
			rilevazione.setBarcode(row.getString("Barcode"));
			rilevazione.setQta(row.getString("Qta"));
			
			 
		 }
		 catch (Exception ex)
		 {
			 System.out.println("Errore in ArticoliMapper.mapRow: " + ex);
		 }
		
 
		return rilevazione;
	}
}

package com.demo.webapp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.demo.webapp.domain.Movimenti;

public class MovimentiMapper  implements RowMapper<Movimenti>
{

	@Override
	public Movimenti mapRow(ResultSet row, int rowNum) throws SQLException 
	{
		Movimenti movimenti = new Movimenti();
		
		try
		{
			movimenti.setCodArt(row.getString("CODART"));
			movimenti.setDesArt(row.getString("DESCRIZIONE"));
			movimenti.setAcquisto(row.getDouble("PRZACQ"));
			movimenti.setUm(row.getString("UM"));
			movimenti.setAcquistato(row.getFloat("ACQUISTATO"));
			movimenti.setReso(row.getFloat("RESO"));
			movimenti.setVenduto(row.getFloat("VENDUTO"));
			movimenti.setUscite(row.getFloat("USCITE"));
			movimenti.setScaduti(row.getFloat("SCADUTI"));
			movimenti.setMagazzino(row.getFloat("QTAMAG"));
			movimenti.setIncscaduti(row.getFloat("INCSCAD")); 
			
		 }
		 catch (Exception ex)
		 {
			 System.out.println("Errore in MovimentiMapper.mapRow: " + ex);
		 }
		
		return movimenti;
	}

}

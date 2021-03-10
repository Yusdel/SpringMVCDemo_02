package com.demo.webapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.webapp.domain.Movimenti;
import com.demo.webapp.repository.MovimentiRepository;

@Service
public class MovimentiServiceImpl implements MovimentiService
{
	@Autowired 
	MovimentiRepository movimentiRepository;

	@Override
	public Movimenti SelMovimentiByCodArt(String CodArt) 
	{
		return movimentiRepository.SelMovimentiByCodArt(CodArt);
	}

	@Override
	public List<Movimenti> SelMovimentiByFilter(String Filtro) 
	{
		return movimentiRepository.SelMovimentiByFilter(Filtro);
	}

	@Override
	public List<Movimenti> SelMovimentiByFilter(String Filtro, String OrderBy, String Tipo, String FilterMag) 
	{
		List<Movimenti> recordset = movimentiRepository.SelArticoliByFilter(Filtro, OrderBy, Tipo);
			
		recordset = recordset
				.stream()
				.filter((FilterMag.equals(">0") ? u -> u.getMagazzino() > 0 :  u -> u.getMagazzino() < 0))
				.collect(Collectors.toList());
		

		return recordset;
	}

}

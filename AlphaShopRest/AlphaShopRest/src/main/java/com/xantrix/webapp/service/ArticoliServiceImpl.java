package com.xantrix.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xantrix.webapp.dao.ArticoliDao;
import com.xantrix.webapp.entities.Articoli;

@Service
@Transactional(readOnly = true)
public class ArticoliServiceImpl  implements ArticoliService
{
	@Autowired 
	ArticoliDao articoliRepository;

	@Override
	public Articoli SelByBarcode(String Ean)
	{
		return articoliRepository.SelByEan(Ean);
	}

	@Override
	public Articoli SelByCodArt(String CodArt)
	{
		return articoliRepository.SelByCodArt(CodArt);
	}

	@Override
	public List<Articoli> SelArticoliByFilter(String Filtro)
	{
		return articoliRepository.SelByDescrizione(Filtro);
	}

	@Override
	public List<Articoli> SelArticoliByFilter(String Filtro, String OrderBy, String Tipo)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void InsArticolo(Articoli articolo)
	{
		Articoli testArt = articoliRepository.SelByCodArt(articolo.getCodArt());
		
		if (testArt == null)
			articoliRepository.Salva(articolo);
		else
			articoliRepository.Aggiorna(articolo);
		
	}

	@Override
	public void DelArticolo(String CodArt)
	{
		Articoli articolo = articoliRepository.SelByCodArt(CodArt);
		
		articoliRepository.Elimina(articolo);
	}

}

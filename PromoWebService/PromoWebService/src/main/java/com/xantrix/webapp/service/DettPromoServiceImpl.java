package com.xantrix.webapp.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xantrix.webapp.entities.DettPromo;
import com.xantrix.webapp.repository.DettPromoRepository;

@Service
@Transactional
public class DettPromoServiceImpl implements DettPromoService
{
	@Autowired
	DettPromoRepository dettPromoRepository;
	
	@Override
	public DettPromo SelDettPromoByCodFid(String CodFid, String CodArt) 
	{
		return dettPromoRepository.SelDettPromoByCodFid(CodFid, CodArt);
	}

	@Override
	public void UpdOggettoPromo(String Oggetto, Long Id) 
	{
		dettPromoRepository.UpdOggettoPromo(Oggetto, Id);
	}

}

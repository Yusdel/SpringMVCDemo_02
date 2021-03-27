package com.xantrix.webapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xantrix.webapp.dao.PromoDao;
import com.xantrix.webapp.entities.Promo;

@Service
@Transactional(readOnly = true)
public class PromoServiceImpl implements PromoService
{

	@Autowired
	private PromoDao promoRepository;
	
	@Override
	public List<Promo> SelTutti()
	{
		return promoRepository.SelTutti();
	}

	@Override
	public Promo SelByIdPromo(String IdPromo)
	{
		 return promoRepository.SelByIdPromo(IdPromo);
	}
	
	@Override
	public List<Promo> SelActivePromo()
	{
		Date today = new Date();
		return promoRepository.SelActivePromo(today);
	}
	
	@Override
	@Transactional
	public void InsPromo(Promo promo)
	{
		promoRepository.Salva(promo);
	}

	@Override
	@Transactional
	public void DelPromo(Promo promo)
	{
		promoRepository.Elimina(promo);
	}

}

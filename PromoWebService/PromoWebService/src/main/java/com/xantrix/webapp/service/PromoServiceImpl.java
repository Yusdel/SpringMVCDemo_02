package com.xantrix.webapp.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xantrix.webapp.entities.Promo;
import com.xantrix.webapp.repository.PromoRepository;

@Service
@Transactional
public class PromoServiceImpl implements PromoService
{
	@Autowired
	PromoRepository promoRepository;

	@Override
	public List<Promo> SelTutti() 
	{
		// findAll = Method of Spring Data Jpa -> Interface JpaRepository 
		return promoRepository.findAll();
	}

	@Override
	public Promo SelByIdPromo(String IdPromo) 
	{
		return promoRepository.findByIdPromo(IdPromo);
	}

	@Override
	public Promo SelByAnnoCodice(String Anno, String Codice) 
	{
		return promoRepository.findByAnnoAndCodice(Integer.parseInt(Anno), Codice);
	}

	@Override
	public List<Promo> SelActivePromo(Date Data) 
	{
		return promoRepository.SelActivePromo(Data);
	}

	@Override
	public void InsPromo(Promo promo) 
	{
		promoRepository.saveAndFlush(promo); // Both Insert and Modify
	}

	@Override
	public void DelPromo(Promo promo) 
	{
		promoRepository.delete(promo);
	}

}

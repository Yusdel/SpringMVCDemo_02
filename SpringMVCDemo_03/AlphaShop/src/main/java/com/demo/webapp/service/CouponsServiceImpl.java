package com.demo.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.webapp.dao.CouponsDao;
import com.demo.webapp.entities.Coupons;

//TODO Generation Type Strategy
@Service("couponsService")
@Transactional
public class CouponsServiceImpl implements CouponsService
{
	@Autowired
	private CouponsDao couponsDao;
	
	
	@Override
	public List<Coupons> SelByCodFidelity(String CodFidelity) 
	{
		return couponsDao.SelByCodFidelity(CodFidelity);
	}

	@Override
	public void Salva(Coupons coupon) 
	{
		couponsDao.Salva(coupon);
	}

}

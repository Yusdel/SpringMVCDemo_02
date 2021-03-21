package com.demo.webapp.dao;

import java.util.List;

import com.demo.webapp.entities.Coupons;

//TODO Generation Type Strategy
public interface CouponsDao 
{
	List<Coupons> SelByCodFidelity(String CodFidelity);
	
	void Salva(Coupons coupon);
}

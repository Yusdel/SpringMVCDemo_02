package com.demo.webapp.service;

import java.util.List;

import com.demo.webapp.entities.Coupons;

//TODO Generation Type Strategy
public interface CouponsService 
{
	List<Coupons> SelByCodFidelity(String CodFidelity);
	
	void Salva(Coupons coupon);
}

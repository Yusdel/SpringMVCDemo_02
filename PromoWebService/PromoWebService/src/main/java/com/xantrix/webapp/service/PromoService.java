package com.xantrix.webapp.service;
import java.util.Date;
import java.util.List;

import com.xantrix.webapp.entities.Promo;

public interface PromoService 
{
	public List<Promo> SelTutti();
	
	public Promo SelByIdPromo(String IdPromo);
	
	public Promo SelByAnnoCodice(String Anno, String Codice);
	
	List<Promo> SelActivePromo(Date Data);
	
	public void InsPromo(Promo promo); // Promo and Linked Classes
	
	public void DelPromo(Promo promo);
}

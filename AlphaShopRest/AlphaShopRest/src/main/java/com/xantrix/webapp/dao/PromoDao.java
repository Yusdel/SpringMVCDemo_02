package com.xantrix.webapp.dao;

import java.util.Date;
import java.util.List;

 
import com.xantrix.webapp.entities.Promo;

public interface PromoDao  
{
	Promo SelByIdPromo(String IdPromo);
	
	List<Promo> SelActivePromo(Date Data); 
	
	List<Promo> SelTutti();
	
	void Salva(Promo promo);

	void Aggiorna(Promo promo);

	void Elimina(Promo promo);
}

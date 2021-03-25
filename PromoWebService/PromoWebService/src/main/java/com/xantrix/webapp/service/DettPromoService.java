package com.xantrix.webapp.service;

import com.xantrix.webapp.entities.DettPromo;

public interface DettPromoService 
{
	DettPromo SelDettPromoByCodFid(String CodFid, String CodArt);
	
	void UpdOggettoPromo(String Oggetto, Long Id);
}

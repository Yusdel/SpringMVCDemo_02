package com.demo.webapp.repository;

import com.demo.webapp.domain.Ingredienti;

public interface IngredientiRepository 
{
	Ingredienti SelIngredientiByCodArt(String CodArt);
	
	void InsIngredienti(Ingredienti ingredienti);
	
	void DelIngredienti(String CodArt);
}

package com.demo.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.webapp.domain.Ingredienti;
import com.demo.webapp.repository.IngredientiRepository;

@Controller
@RequestMapping("/ingredienti")
public class IngredientiController 
{
	@Autowired
	private IngredientiRepository ingredientiRepository;

	// @RequestMapping(value = "/aggiungi", method = RequestMethod.GET)
	@GetMapping(value = "/aggiungi/{codart}")
	public String InsIngr(Model model, @PathVariable("codart") String CodArt) 
	{
		Ingredienti ingredienti = ingredientiRepository.SelIngredientiByCodArt(CodArt);
		
		model.addAttribute("Titolo", "Inserimento Informazioni/Ingredienti");
		model.addAttribute("Titolo2", " Articolo " + CodArt);
		model.addAttribute("Ingredienti", ingredienti);

		return "insIngredienti";
	}

	@PostMapping(value = "/aggiungi/{codart}")
	public String GestInsIngr(@ModelAttribute("Ingredienti") Ingredienti ingredienti, 
			@PathVariable("codart") String CodArt) 
	{
		ingredienti.setCodArt(CodArt);
		
		ingredientiRepository.InsIngredienti(ingredienti); 
		
		return "redirect:/articoli/cerca/" + ingredienti.getCodArt();
	}

}

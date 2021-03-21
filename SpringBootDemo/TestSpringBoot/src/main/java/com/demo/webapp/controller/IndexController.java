package com.demo.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/") // root
public class IndexController {
	
	@GetMapping // baseUrl = "/"
	public String getWelcome(Model model) {
		this.SetModel(model);
		
		return "index";
	}
	 
	@GetMapping(value = "index") // baseUrl = "index"
	public String getWelcome2(Model model) {
		this.SetModel(model);
		
		return "index";
	}
	
	private Model SetModel(Model model) {
		
		model.addAttribute("intestazione", "Welcome into Hell");
		model.addAttribute("saluti", "Select your life!");
		
		return model;
	}
}

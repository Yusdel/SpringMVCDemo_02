package com.demo.webapp.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.webapp.entities.Clienti;
import com.demo.webapp.entities.Coupons;
import com.demo.webapp.service.ClientiService;
import com.demo.webapp.service.CouponsService;

@Controller
@RequestMapping("/coupons")
public class CouponsController 
{
	@Autowired
	private CouponsService couponsService;
	
	@Autowired
	private ClientiService clientiService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(CouponsController.class);
	
	@GetMapping(value = "/aggiungi/{idCliente}")
	public String InsCoupon(@PathVariable("idCliente") String IdCliente, Model model)
	{
		
			logger.info("Inserimento Coupon Cliente con Codice: " + IdCliente);

			if (!IdCliente.equals("-1"))
			{
				Clienti cliente = clientiService.SelCliente(IdCliente);
				
				Coupons coupon = new Coupons();
				
				coupon.setCliente(cliente);
				coupon.setIdDeposito(1);
				coupon.setQta(2);
				coupon.setValore(10);
				coupon.setDataCreaz(new Date());
				
				couponsService.Salva(coupon);
				
			}
			
		
		return "redirect:/clienti/search?filter=67301895";

	}
}

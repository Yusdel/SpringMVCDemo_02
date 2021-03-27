package com.xantrix.webapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xantrix.webapp.entities.Promo;
import com.xantrix.webapp.service.PromoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/promo")
@Slf4j
public class PromoRestController
{
	//private static final Logger logger = LoggerFactory.getLogger(PromoRestController.class);

	@Autowired
	private PromoService promoService;

	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Promo>> listAllPromo()
	{
		log.info("****** Otteniamo tutte le promozioni *******");

		List<Promo> promo = promoService.SelTutti();

		if (promo == null)
		{
			return new ResponseEntity<List<Promo>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Promo>>(promo, HttpStatus.OK);
	}

	@RequestMapping(value = "/id/{idPromo}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Promo> listPromoById(@PathVariable("idPromo") String IdPromo)
	{
		log.info("****** Otteniamo la promozione con Id: " + IdPromo + "*******");

		Promo promo = promoService.SelByIdPromo(IdPromo);

		if (promo == null)
		{
			return new ResponseEntity<Promo>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Promo>(promo, HttpStatus.OK);
	}

	@RequestMapping(value = "/active", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Promo>> listPromoActive()
	{
		log.info("****** Otteniamo la Promozione Attive*******");

		List<Promo> promo = promoService.SelActivePromo();

		if (promo == null)
		{
			return new ResponseEntity<List<Promo>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Promo>>(promo, HttpStatus.OK);
	}

	/*
	 * // ------------------- INSERT PROMO ------------------------------------
	 * 
	 * @RequestMapping(value = "/inserisci", method = RequestMethod.PUT,
	 * consumes = "application/json", headers =
	 * "content-type=application/x-www-form-urlencoded") public
	 * ResponseEntity<Promo> createPromo(@RequestBody Promo promo) {
	 * logger.info("Creiamo una Promo con id " + promo.getIdPromo());
	 * 
	 * promoService.InsPromo(promo);
	 * 
	 * HttpHeaders headers = new HttpHeaders(); //
	 * headers.setLocation(ucBuilder.path("/promo/id/{idPromo}").buildAndExpand(
	 * promo.getIdPromo()).toUri());
	 * 
	 * return new ResponseEntity<Promo>(headers, HttpStatus.CREATED); }
	 */

	@RequestMapping(value = "/inserisci", method = RequestMethod.POST, consumes = "application/json", headers = "content-type=application/x-www-form-urlencoded")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void create(@RequestBody Promo promo)
	{
		log.info("Creiamo una Promo con id " + promo.getIdPromo());

		promoService.InsPromo(promo);
	}

	// ------------------- DELETE PROMO ----------------------------------
	@RequestMapping(value = "/{idPromo}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePromo(@PathVariable("idPromo") String IdPromo)
	{
		log.info("Eliminiamo la promo con id " + IdPromo);

		HttpHeaders headers = new HttpHeaders();
		ObjectMapper mapper = new ObjectMapper();

		headers.setContentType(MediaType.APPLICATION_JSON);

		ObjectNode responseNode = mapper.createObjectNode();

		Promo promo = promoService.SelByIdPromo(IdPromo);

		if (promo == null)
		{
			log.info("ERRORE: Impossibile trovare la promo con id " + IdPromo);

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		promoService.DelPromo(promo);

		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Eliminazione Promozione" + IdPromo + " Eseguita Con Successo");

		return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
	}

}

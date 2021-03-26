package com.xantrix.webapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.xantrix.webapp.entities.DettPromo;
import com.xantrix.webapp.entities.Promo;
import com.xantrix.webapp.repository.PromoRepository;

/*
 * Note.
 * The test class must check every element of the class under examination.
 * Each Unit Test must test a specific method.
 */

@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = Application.class)
@SpringBootTest
public class PromoRepositoryTest 
{
	@Autowired
	private PromoRepository promoRepository;
	
	@Test
	public void TestfindByIdPromo()
	{
		String IdPromo = "C9517A3E-5FCE-464F-8DAF-C4485CD2D925";
		
		assertThat(promoRepository.findByIdPromo(IdPromo)) // call DB with the method to be tested
		.extracting(Promo::getDescrizione) // get description
		.isEqualTo("PROMO TEST1"); // condition of success
		
		
	}
	
	@Test
	public void TestfindByAnnoAndCodice()
	{
		int Anno = 2018;
		String Codice = "TEST02";
		
		assertThat(promoRepository.findByAnnoAndCodice(Anno, Codice))
		.extracting(Promo::getDescrizione)
		.isEqualTo("PROMO TEST1");
		
	}
	
	@Test
	public void TestSelActivePromo()
	{
		Date today = new Date();
		
		List<Promo> items = promoRepository.SelActivePromo(today);
		assertEquals(7, items.size());
		
	}
	
	@Test
	public void TestSelActivePromo2()
	{
		Date today = new Date();
		
		assertThat(promoRepository.SelActivePromo(today).get(0))
		.extracting(Promo::getDescrizione)
		.isEqualTo("PROMO FIDELITY ONLY YOU");
	}
	
}

package com.demo.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import  org.springframework.security.core.userdetails.User;

import com.demo.webapp.entities.Utenti;

import org.apache.commons.lang.StringUtils;

@Service("customUserDetailsService") // param = name of service class
public class CustomUserDetailsService implements UserDetailsService
{
	@Autowired
	private UtentiService utenteService;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String UserId) 
			throws UsernameNotFoundException
	{
		 String[] UsernameAndCodFid = StringUtils.split(UserId, "@"); // yMorales@67301894
		 
		 if (UsernameAndCodFid == null || UsernameAndCodFid.length != 2) 
		 {
	            throw new UsernameNotFoundException("Devono essere inserite la Userid e la Fidelity del cliente");
		 }
		 
		 String userId = UsernameAndCodFid[0];
		 String codFid = UsernameAndCodFid[1];
		 
		 Utenti utente = utenteService.SelByUserIdCodFid(userId, codFid);
		 
		 if (utente == null)
		 {
			 throw new UsernameNotFoundException("Utente non Trovato!!");
		 }
		 
		 UserBuilder builder = null;
		
		 builder = User.withUsername(utente.getUserId());
		 builder.disabled((utente.getAbilitato().equals("Si") ? false : true ));
		 builder.password(utente.getPwd());
		 
		 String[] profili = utente.getProfili()
				 .stream()
				 .map(a -> "ROLE_" + a.getTipo())
				 .toArray(String[]::new); // convert collection in Array
		 
		 builder.authorities(profili);
		 
		 return builder.build();
	}
	 
}

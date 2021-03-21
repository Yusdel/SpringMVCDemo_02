package com.demo.webapp.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.demo.webapp.dao.UtentiDao;
import com.demo.webapp.entities.Utenti;

/*
 * TODO Custom Validation if client exist
 */
public class UserIdValidator  implements ConstraintValidator<UserId, String>
{
	
	@Autowired
	private UtentiDao utentiDao;
	
	@Override
	public boolean isValid(String InsertedUserId, ConstraintValidatorContext theConstraintValidatorContext)
	{

		boolean retVal = true;
		
		Utenti utente = utentiDao.SelByUserId(InsertedUserId);

		if (utente != null)
		{
			retVal = false;
		} 
		 
		return retVal;
	}
}

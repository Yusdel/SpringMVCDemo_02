package com.demo.webapp.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CodArtValidator  implements ConstraintValidator<CodArt, String>{
	
	private String codArtPrefix;
	
	@Override
	public void initialize(CodArt Codice) {
		codArtPrefix = Codice.matrice();
	}

	@Override
	public boolean isValid(String InsertedCodArt, ConstraintValidatorContext theConstraintValidatorContext) {
		
		boolean retVal = false;
		
		if(InsertedCodArt != null && !InsertedCodArt.equals(""))
			retVal = codArtPrefix.equals(InsertedCodArt.substring(0,4));
		
		return retVal;
	}
}

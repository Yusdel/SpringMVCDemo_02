package com.demo.webapp.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/*
 * TODO Custom Validation if client exist
 */
@Target( { METHOD, FIELD, ANNOTATION_TYPE }) 
@Retention(RUNTIME) 
@Constraint(validatedBy = UserIdValidator.class) // Classe in cui verrà effettuata la validazione
@Documented
public @interface UserId 
{
	public String message() default "{UserId.Clienti.userId.validation}";
	
	public Class<?>[] groups() default {}; 
    
    public abstract Class<? extends Payload>[] payload() 
    
    default {};
   
}

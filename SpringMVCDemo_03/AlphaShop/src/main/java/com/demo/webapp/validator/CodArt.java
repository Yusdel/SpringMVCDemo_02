package com.demo.webapp.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * New @Annotation
 * @author Yusdel Morales
 */

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CodArtValidator.class) /* Class that will perform the validation */
@Documented
public @interface CodArt {
	
	/* Path/Name in messages.properties */
	public String message() default "{CodArt.Articoli.codArt.validation}";
	
	public Class<?>[] groups() default {};
	
	public abstract Class<? extends Payload>[] payload()
	
	default {};
	
	/* Mean that each item code must start with the String ART-, otherwise custom validation will be triggered */
	public String matrice() default "ART-";
	
}

package com.xantrix.webapp.exception;

import lombok.Data;

/*
 * Custom error response 
 */
@Data
public class ErrorResponse 
{
	private int codice;
	private String messaggio;
	
}

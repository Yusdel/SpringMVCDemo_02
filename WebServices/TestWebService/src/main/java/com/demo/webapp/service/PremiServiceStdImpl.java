package com.demo.webapp.service;

import org.springframework.stereotype.Service;

// TODO Code Injection

@Service("STD") // stringa che indentifica questo servizio
public class PremiServiceStdImpl implements PremiService{

	@Override
	public double GetPremio() {
		return 100;
	}

}

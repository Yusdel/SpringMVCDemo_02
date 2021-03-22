package com.demo.webapp.service;

import org.springframework.stereotype.Service;

// TODO Code Injection

@Service("TOP")
public class PremiServiceTopImpl implements PremiService {
	
	@Override
	public double GetPremio() {
		return 1000;
	}
}

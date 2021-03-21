package com.demo.webapp.service;

import org.springframework.stereotype.Service;

@Service("TOP")
public class PremiServiceTopImpl implements PremiService {
	
	@Override
	public double GetPremio() {
		return 1000;
	}
}

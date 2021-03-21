package com.demo.webapp.service;

import org.springframework.stereotype.Service;

@Service("STD")
public class PremiServiceStdImpl implements PremiService{

	@Override
	public double GetPremio() {
		return 100;
	}

}

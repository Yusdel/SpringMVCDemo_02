package com.demo.webapp.service;

import java.util.List;
 
import com.demo.webapp.entities.Profili;

/*
 * STEP B-Using: TODO Hibernate and JPA Framework
 */

public interface ProfiliService
{
	Profili SelById(int id);
	
	List<Profili> SelByIdFidelity(String IdFidelity);
	
	void Salva(Profili profilo);

	void Elimina(Profili profilo);
	
	void Aggiorna(Profili profilo);
}

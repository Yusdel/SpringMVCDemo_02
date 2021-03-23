package com.demo.webapp.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.demo.webapp.domain.Rilevazioni;
import com.demo.webapp.repository.ArticoliRepository;
import com.demo.webapp.repository.RilevazioniRepository;

/*
 * @Transactional = All operations in this class can be subject to transaction. In this way,
 * it's possible to carry out a total rollback of several calls made within this class.
 * 
 * If the rollback isn't done, Spring still writes the data it managed to write to the Database, 
 * simply skipping the failed transactions.
 * 
 * Propagation.REQUIRES_NEW = Se vi è una transazione pending, proveniente da un'altro processo, si rischia di andare ad annullare
 * anche quell'altra transazione che non è magari correlata con questa business logic. In questi casi si crea una nuova transazione 
 * e si mette in pausa l'altra, così in caso di rollback verrà annullata solo questa.
 * 
 * Isolation.READ_COMMITTED = Tipo di isolamento che la nostra transazione può gestire. Serve a gestire le chiamate da parte di ALTRI
 * Thread agli stessi dati che si stanno scrivendo.
 */

@Component
//@Transactional // TODO Transactional
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW) // TODO Transactional
public class GestTrasmissioni 
{
	@Autowired 
	RilevazioniRepository rilevazioniRepository;
	
	public int GestFile(String Path, String File)
	{
		
		int righe = 0;
		String IdTerminale = File.substring(0,5);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String Data = dateFormat.format(date);
		
		Path datiPath = Paths.get(Path, File);
	    Charset charset = Charset.forName("ISO-8859-1");
	        
	    List<String> lines = null;
	    
	    try 
        {
            lines = java.nio.file.Files.readAllLines(datiPath, charset);
        } 
        catch (IOException e1) 
        {  
            e1.printStackTrace();
	    }
	    
	    rilevazioniRepository.DelTrasm(IdTerminale);
	    
	    for (String line : lines)
	    {
	    	if (line.length() > 22 && line.substring(0, 2).equals("OK"))
	    	{
		    	String Barcode = line.substring(2,15);
		    	Integer Qta = Integer.parseInt(line.substring(17,22));	
		    	
		    	try
		    	{
		    		// TODO Transactional
		    		if(Qta > 100) {
		    			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		    			righe = 0;
		    			break;
		    		}
		    		
		    		rilevazioniRepository.InsTrasm(new Rilevazioni(Data,IdTerminale,Barcode,Qta.toString()));
		    		righe++;
		    	}
		    	catch (Exception ex)
		    	{
		    		System.out.println(ex.getMessage());
		    		// TODO Transactional
		    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		    		righe = 0;
		    		break;
		    	}
	    	}
	    }
	    
	    return righe;
	}
}

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

import com.demo.webapp.domain.Rilevazioni;
import com.demo.webapp.repository.ArticoliRepository;
import com.demo.webapp.repository.RilevazioniRepository;

@Component
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
		    		rilevazioniRepository.InsTrasm(new Rilevazioni(Data,IdTerminale,Barcode,Qta.toString()));
		    		righe++;
		    	}
		    	catch (Exception ex)
		    	{
		    		System.out.println(ex.getMessage());
		    	}
	    	}
	    }
	    
	    return righe;
	}
}

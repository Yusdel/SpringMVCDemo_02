package com.demo.webapp.domain;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author Yusdel Morales
 * To upload file
 * 
 * TODO Reading and Processing a file 
 */
public class Trasmissioni implements Serializable
{
	private static final long serialVersionUID = 7650154558839481243L;
	
	private MultipartFile fileTerminale;

	public MultipartFile getFileTerminale() {
		return fileTerminale;
	}

	public void setFileTerminale(MultipartFile fileTerminale) {
		this.fileTerminale = fileTerminale;
	} 
	
	

}

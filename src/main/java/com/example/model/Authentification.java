package com.example.model;

import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="authentification")
public class Authentification {
	

	@Id
	private Long id;
	private String adresse;
	private String cle;
	private long time;
	
	public Authentification() {
		super();
		this.time=System.currentTimeMillis();
	}
	public Authentification(Long id, String adresse, String cle) {
		super();
		this.id = id;
		this.adresse = adresse;
		this.cle = cle;
		this.time=System.currentTimeMillis();
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getCle() {
		return cle;
	}
	public void setCle(String cle) {
		this.cle = cle;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}

	
}

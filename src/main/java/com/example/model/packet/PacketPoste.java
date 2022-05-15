package com.example.model.packet;

public class PacketPoste {

	private String adresse;
	private String cle;
	
	@Override
	public String toString() {
		return "PacketPoste [adresse=" + adresse + ", cle=" + cle + "]";
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
}

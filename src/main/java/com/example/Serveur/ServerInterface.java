package com.example.Serveur;

import java.util.List;

public interface ServerInterface {
	public void lancer() ;
	public void stop_serveur(int NClient) ;
	public boolean creatPost(String adresse,String cle,List<String> topic);
}

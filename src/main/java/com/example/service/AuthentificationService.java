/*cette classe montionne tout les service de lauthentification
 * qui interagir avec la base de donnes mongodb
 */
package com.example.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AuthentificationRepo;
import com.example.Serveur.ServerMetier;
import com.example.model.*;

@Service
public class AuthentificationService {
//implements AuthentificationInterface{
	
	public AuthentificationService()
	{
		System.out.println("Class authService cree");
	}	
	@Autowired
	 private AuthentificationRepo authenrepo;
	
	//return tt les postes
	public List<Authentification> getall()
	{
		return authenrepo.findAll();		
	}
	
	//ajouter un poste avec adresse et cle
	public void addPoste(String adresse,String cle) {
		
		Authentification poste_max=authenrepo.findFirstByOrderByIdDesc();
		
	
		Authentification poste = new Authentification();
		poste.setId(poste_max.getId()+1);	
		poste.setCle(cle);
		poste.setAdresse(adresse);
		authenrepo.save(poste);
		ServerMetier.hmap_adresse_cle=getKeyHash();//mise a jour de map des cles
		}
	
	//recuperer une Map depuis la liste de mongo adresse avec cle
	public HashMap<String, String> getKeyHash()
	{
		
		List<Authentification> list=getall();
		HashMap<String, String> hmap=new HashMap<String, String>();
		
		for(Authentification authen : list) 
		{
			hmap.put(authen.getAdresse(), authen.getCle());
		}
		return hmap;
	}
	
	public void removePoste(String adresse) {
		Authentification auth=authenrepo.findByAdresse(adresse);
		authenrepo.delete(auth);
	}

	
	//cette methode prmet de virifier si un client (consommateur ou producteur) a le droit de ce connecte
	public String verification(Socket client,ObjectInputStream in) throws ClassNotFoundException, IOException {
		String cle="vide";
		
		try {
		//	lire la cle dapres la socket
		cle=(String) in.readObject();
		String adresse=client.getInetAddress().toString();
		adresse=adresse.split("/")[1];	
		if(ServerMetier.hmap_adresse_cle.get(adresse).equals(cle)) {
			
			return ServerMetier.hmap_adresse_cle.get(adresse);
		}
		else {System.out.println("la cle >"+cle+"< de "+adresse+" est non valide");
				return "vide";
		}
		
		}
		catch (Exception e) {
			System.out.println("probleme au niveau d'authentification il oeut que le client n'existe pas");
		}
		return null;	
	}
	



}

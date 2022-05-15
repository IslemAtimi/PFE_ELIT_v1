/*cette class a le role pour valide les clients et affecte chaqun 
 * a sa tache (producteur/consommateur)
 * */

package com.example.Threads;

import java.beans.JavaBean;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.Serveur.ServerMetier;
import com.example.consommateur.ConsommateurMetier;
import com.example.consommateur.ThreadConsumer;
import com.example.model.DataConfig;
import com.example.model.TopicData;
import com.example.producteur.ProducteurMetier;
import com.example.service.AuthentificationService;
import com.example.service.TopicDataService;
import com.example.service.UserService;

import jdk.internal.util.xml.impl.Input;


@Component
@Scope("prototype")
public class ThreadS extends Thread {

	private int N_client;
	private Socket server;
	private String name;
	
	@Autowired
	private AuthentificationService authentificationService;
	@Autowired
	private ProducteurMetier producteurMetier;
	@Autowired
	private ConsommateurMetier consommateurMetier;
	@Autowired
	private TopicDataService topicDataService;
	@Autowired
	private ApplicationContext context;
	@Autowired
	private UserService userService;
	
	
	public ThreadS() {
		System.out.println("HI i am a new Thread ");
	}

	public ThreadS(Socket server,int n_client) {
		this.N_client = n_client;
		this.server = server;
	}
	public int getN_client() {
		return N_client;
	}

	public void setN_client(int n_client) {
		N_client = n_client;
	}

	public Socket getSocket() {
		return server;
	}

	public void setSocket(Socket server) {
		this.server = server;
	}

	@Override
	public void run() {
		
		System.out.print("Le client : "+N_client+"\t L'@ : "+server.getInetAddress()+"\t Port : "+server.getPort()+"\n");
	  
		ObjectOutputStream out=null;
		ObjectInputStream in=null;
		String line=null;
        List<String> packet_receve=null;	//packet receve depuis le client a chaque fois
        int i=0;

		try {
			out=new ObjectOutputStream(server.getOutputStream());
			in = new ObjectInputStream(server.getInputStream());
       	 //verifier le client si il est autorise 
  			String acess=authentificationService.verification(server,in);
  			if(acess.equals("vide")) {System.out.println("client refuse");System.exit(0);}
  			
  			//recupere le name de client (client+id)
  			this.name="client_"+(String) in.readObject();
  			String consumer=(String) in.readObject();//si true il consomme
  			
  			chargerTopicClient(N_client ,this.name);//recupere les topic de client
  			Insert_topic_ACK(N_client,consumer);//pour remplire les ACK
    		Insert_topic_offset(N_client,this.name);//inserer les offset des topic dun seul client
  		
			//ACK client accepte vers le client
			out.writeObject("CLINET_ACCEPT");
			
			 //cree un thread de  microservice 
			ServerMetier.consummer_map.put(N_client,(ThreadConsumer)context.getBean(ThreadConsumer.class));//cree le bean de thread
			ServerMetier.consummer_map.get(N_client).setName("client_"+N_client);
			ServerMetier.consummer_map.get(N_client).setId_client(N_client);
			ServerMetier.consummer_map.get(N_client).setCannaux(server);
			ServerMetier.consummer_map.get(N_client).setOut(out);
			ServerMetier.consummer_map.get(N_client).start();
    	
    
    		Thread.sleep(3000);//pour que le ThreadConsummer install & le client aussi
		
			while(true) {		
				
			packet_receve=(List<String>) in.readObject();	      

	        if(packet_receve.get(0).equals("C"))//alors c une ACK
	        {	        	
	        	//incrementer le offset de topic_Ofsset
	        	int nv_offset=ServerMetier.Topic_Offset.get(N_client).get(packet_receve.get(1)); 
	        	nv_offset++;
	        	ServerMetier.Topic_Offset.get(N_client).replace(packet_receve.get(1), nv_offset);
	        	//inserer le ACK
	        	ServerMetier.ACK_consummer.get(N_client).add("ACK_"+packet_receve.get(1));

	        }
	       else {
	    	   System.out.println("salut producteur : "+i+"\n");
	    	   producteurMetier.produce(out, server, packet_receve.get(1),packet_receve.get(2));
	    	   System.out.println("les donnes sont bien arrive");
	    	 
	        	}
			
		}
			
			
		} catch (ClassNotFoundException |InterruptedException| IOException e) {
			// TODO Auto-generated catch block
			System.out.println("un client a quitte ");
			/*sauvgarde les offset dans la base*/
			System.out.println("--- topic offset ---"+ServerMetier.Topic_Offset);
			userService.saveOffsetClient(this.N_client,this.name);
			
			/*supprimer tout les ACK_consummer*/
			System.out.println("--- ACK_consummer ---"+ServerMetier.ACK_consummer);
			ServerMetier.ACK_consummer.remove(this.N_client);
			ServerMetier.Topic_Offset.remove(this.N_client);
		
	
		System.out.println("le client "+N_client+" a bien quitter");
      
          
	}

	}
	  
	   //remplire la map des offsset de chaque topic
	   private void Insert_topic_offset(int idClient,String name) {
		HashMap<String, Integer> map=new HashMap<String, Integer>();

		map=userService.getTopicOffset(name);
		ServerMetier.Topic_Offset.put(idClient, map);
		System.out.println("la map des offset est bien cree\n"+ServerMetier.Topic_Offset);
	}
	//remplire les ACK de client pour la 1er fois 
	private void Insert_topic_ACK(int idClient,String consumer) {
		List<String> L=new ArrayList<String>();
		for(String topic:ServerMetier.Topic_client.get(idClient)) {
		if(consumer.equals("true"))//si il veut consommer on lui ajout les ACK
		L.add("ACK_"+topic);
		ChargeTopic_ifNotExist(topic);		//insere les donne dans la map si il nexiste pas 
		}
		ServerMetier.ACK_consummer.put(idClient, L);
		System.out.println("la map de ACK est bien ete cree\n"+ServerMetier.ACK_consummer);
	}
	//remplire les topic dun client
	private void chargerTopicClient(int IdClient,String name) {
		List<String> list=new ArrayList<String>();

		list=userService.getTopicClient(name);
		ServerMetier.Topic_client.put(IdClient, list);
		System.out.println("le topic client est"+ServerMetier.Topic_client);
	}
	
//charger les donnes depuis les offset des donnes
	public void ChargeTopic_ifNotExist(String topic) {
		 if (!topicDataService.map_data.containsKey(topic))
		 {
		 List<TopicData> data=topicDataService.getAllData(topic);
		 topicDataService.map_data.put(topic,  new HashMap<Integer, String>());
		 
		 int i=Integer.parseInt(ServerMetier.OffsetMaxi.get(topic));
		 for(TopicData s:data) {
			 topicDataService.map_data.get(topic).put(i,s.getData());//ajout par id depuis le miniOffset
			 i++;
		 }
		 ServerMetier.OffsetMaxi.replace(topic, Integer.toString(i));
		 }		
		 System.out.println(topicDataService.map_data);
	}

	
}

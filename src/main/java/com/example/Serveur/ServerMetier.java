/*
 * cette classe consiste de donner a chaque client un threads
 * afin de manipuler le systeme de messagerie
 */
package com.example.Serveur;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import com.example.ProjectCsApplication;
import com.example.Threads.ManagerServer;
import com.example.Threads.ThreadS;

import com.example.config.ThreadConfig;
import com.example.consommateur.ThreadConsumer;
import com.example.model.Authentification;
import com.example.model.User;
import com.example.model.TopicData;
import com.example.service.AuthentificationService;
import com.example.service.DataConfigService;
import com.example.service.TopicDataService;
import com.example.service.UserService;


@Service
public class ServerMetier {
	@Autowired
	private AuthentificationService authenservice;
	@Autowired
	private ApplicationContext context;
	@Autowired
	private DataConfigService dataConfigService;
	@Autowired
	private TopicDataService topicDataService;

	public static HashMap<Integer, ThreadS> map_thread;	//une map pour les thread
	public static HashMap<String, String> hmap_adresse_cle;	//une map des adresse ip avec ses cle
	public static HashMap<Integer, ThreadConsumer> consummer_map;//la map de chaque client a un Thread consommateur 
	public static HashMap<Integer, List<String>> ACK_consummer;//chaque client a une liste de demande selon leur arrive
	public static HashMap<Integer, List<String>> Topic_client;//la liste des topic de chaque client
	public static HashMap<Integer, HashMap<String, Integer>> Topic_Offset;//les offset de chaque client dun topic 
	
	public static HashMap<String , String> OffsetMini;
	public static HashMap<String , String> OffsetMaxi;
	public static int Max_Thread;
	public static int MAX_Time_Sleep;
	public static int MAX_Data;
	public static String CollectionsConfig;
	
	
	public ServerMetier() {}
	
   public void lancer() throws IOException   {
	   //charger la configuration standard
	   dataConfigService.chargerConfig(0);
	   System.out.println(OffsetMini+" "+Max_Thread+" "+MAX_Time_Sleep+" "+MAX_Data+" "+CollectionsConfig);
	   
	   
	   int IdClient=1; 
	 
	   hmap_adresse_cle=authenservice.getKeyHash();
	   System.out.println(hmap_adresse_cle);
       ServerSocket server=null ;
       Socket socketOfServer;
       consummer_map=new HashMap<Integer, ThreadConsumer>();
       ACK_consummer=new HashMap<Integer, List<String>>();
       Topic_client=new HashMap<Integer, List<String>>();
       Topic_Offset=new HashMap<Integer, HashMap<String,Integer>>();
       
       server = new ServerSocket(8085);
             
       //creation de gestionnaire (le declanchment demare dés le lancment de 1er client)
       ManagerServer manager=(ManagerServer)context.getBean(ManagerServer.class);//cree le bean de thread manager
       manager.setName("Gestionnaire_Server");
       //manager.start();
       
       
       map_thread=new HashMap<Integer, ThreadS>();
       ThreadS thread_client;
       System.out.println("The ServerMetier is waiting a new connection..!");
       while(IdClient<Max_Thread) {
           
           try {
        	   socketOfServer = server.accept();
        	   System.out.println("Server accept user..."+IdClient+"\n");
        	   

   				System.out.println("Client accepte : "+socketOfServer.getInetAddress());   
        	    
   				thread_client =(ThreadS)context.getBean(ThreadS.class);//cree le bean de thread
        		thread_client.setName("Thread_"+IdClient);
        		thread_client.setN_client(IdClient);
        		thread_client.setSocket(socketOfServer);//affcete la socket au bean
        		map_thread.put(IdClient, thread_client);
        		map_thread.get(IdClient).start();//demarre le thread (bean)
        		
   
        	   
        	   System.out.println("----------Server waiting a new connection------------");
               }
           
           catch(Exception e) {
        	   System.out.println("probleme dans le serveur des threads :"+e);
           	}
           
           IdClient++;
           }
           
       }



/*une methode pour arrete tt les threads*/
  public void stop_serveur() { 
	  
	/*arrete les thread*/  
	for(int i=0;i<=map_thread.size()-1;i++) {
	try {
	   map_thread.get(i).stop();
	}catch (Exception e) {}
	}
	System.out.println("tous les thread sont bien arrete");   
	
	/*sauvgarder les mini offset*/
	dataConfigService.saveOffsetMini(0);
	System.out.println("les offset mini sont bien sauvgarder");
	/*charger les donnes dans mongo*/
	for(String topic:topicDataService.listTopic())
	{int taille=Integer.parseInt(OffsetMaxi.get(topic))-Integer.parseInt(OffsetMini.get(topic));
	if(taille>MAX_Data)taille=MAX_Data;
	topicDataService.StockMongo(topic, taille);
	}
	System.out.println("les donnes sont bien stocke");
	
    System.out.println("Sever stopped!");
    System.exit(0);
   
  }
   




}
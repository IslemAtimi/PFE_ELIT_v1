package com.example.service;

import java.nio.charset.Charset;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Serveur.ServerMetier;
import com.example.Threads.ThreadS;
import com.example.model.User;
import com.example.model.DataConfig;
import com.example.model.TopicData;
import com.example.repository.*;

@Service
public class TopicDataService {
	 

	@Autowired
	 private AuthentificationRepo authenrepo;
	@Autowired
	private TopicDataRepo topicDataRepo;
	
	
	public  static HashMap<String, HashMap<Integer, String>> map_data=new HashMap<String, HashMap<Integer,String>>(); //topic et data
	
	//recuperer longeur de data base des client
	public int NombreClientGeneral() {
		return (int) authenrepo.count();
	}
	
	//recupere tout les donnes dun topic
	public List<TopicData> getAllData(String topic){
		return topicDataRepo.getAll(topic);
	}
	//recupere les donnes a partir dun intervall sup et inf
		public List<TopicData> getDataByIntervall(String topic,int id_sup,int id_inf){
			return topicDataRepo.getIdIntervall(topic, id_inf, id_sup);
		}

	//save des donnes dun topic pour generer automatiquement les donnes dans DataBase
	public void UploadData(String topic,String adresseClient,String taille) {
		
		List<TopicData> T=new ArrayList<TopicData>();
		int t=Integer.parseInt(taille);
		for(int i=0;i<t;i++)
		{
		TopicData data=new TopicData((long) i,topic,"test_"+i,adresseClient, new Date());
		T.add(data);
		}
		LocalTime deb = LocalTime.now();
		topicDataRepo.saveAllData(topic, T);
		LocalTime fin = LocalTime.now();
		System.out.println("la liste a bien remplie dans :"+ChronoUnit.NANOS.between(deb, fin)+ " NanoSeconde");
		
	}
	
	//ajouter une collection dun topic
	public void AddTopic(String topic) {
		
		topicDataRepo.createCollection_ifNotExist(topic);
		System.out.println("le topic est bien ete genere");
		}
	public void deleteData(String topic) {
		topicDataRepo.deleteAllData(topic);
	}
	
	public List<String> listTopic(){
		
		return topicDataRepo.listTopic();
	}
	/*supprimer les donnes de la memoire a partie de MiniOffsetConf jusqau MiniOfsset*/
	public void deleteDataMem(String topic,int FinOffset){
		int DebutOffset=Integer.parseInt(ServerMetier.OffsetMini.get(topic));
		
		
		while(DebutOffset!=FinOffset) {
			map_data.get(topic).remove(DebutOffset);//suppression 
			DebutOffset++;
		}
		ServerMetier.OffsetMini.replace(topic,Integer.toString(DebutOffset));
	}
	//stock les derniers elements dans le mongo
	public void StockMongo(String topic,int taille) {
		List<TopicData> AllData=new ArrayList<TopicData>();
		int BigOffset=Integer.parseInt(ServerMetier.OffsetMaxi.get(topic));
		
		for(int i=0;i<taille;i++)
		{int id=BigOffset-taille+i;
		TopicData data=new TopicData((long)id,topic,map_data.get(topic).get(id),"127.0.0.1", new Date());
		AllData.add(data);
		}
		LocalTime deb = LocalTime.now();
		topicDataRepo.saveAllData(topic, AllData);
		LocalTime fin = LocalTime.now();
		System.out.println("la liste a bien remplie dans :"+ChronoUnit.NANOS.between(deb, fin)+ " NanoSeconde");
	}
	
	//le min dun offset dans un topic
	public int MinOffsetTopic(String topic) {
		List<Integer> list_offset_topic=new ArrayList<Integer>();
		for (Entry<Integer, HashMap<String, Integer>> entry : ServerMetier.Topic_Offset.entrySet()) {//parcourir les clients
			HashMap<String, Integer> list_client = entry.getValue();
			
			if(list_client.containsKey(topic))list_offset_topic.add(list_client.get(topic));
			
		}
		int min_offset=Collections.min(list_offset_topic); 
		return min_offset;
	}


	


}

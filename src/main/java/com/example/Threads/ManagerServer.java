package com.example.Threads;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.Serveur.ServerMetier;
import com.example.service.DataConfigService;
import com.example.service.TopicDataService;

@Component
@Scope("prototype")
public class ManagerServer extends Thread {

	@Autowired
	private DataConfigService dataConfigService;
	
	int MAX_timeSleep=ServerMetier.MAX_Time_Sleep;	
	int MAX_Data=ServerMetier.MAX_Data;
	
	@Autowired
	private TopicDataService topicDataService;
	
	
	public ManagerServer() {
		System.out.println("***** je suis gestionnaire ******");
		
	}



	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(MAX_timeSleep*1000);
				
				for(Entry<String, HashMap<Integer, String>> map_data:topicDataService.map_data.entrySet()) {//parcourir les topic
					String topic = map_data.getKey();
					HashMap<Integer, String> data = map_data.getValue();
					
					if(data.size()>=MAX_Data){
						System.out.println("la zone memoire de topic <<"+map_data.getKey()+">> il faut sauvgarder une partie est supprimmer le reste");
						/*supprimer le topic*/
						topicDataService.deleteData(topic);
						
						/*stocke sur mongo*/
						topicDataService.StockMongo(topic,ServerMetier.MAX_Data);
						
						/*verifier pour chaque topic le dernier client dans la ram pour efface*/	
						int min_offset=topicDataService.MinOffsetTopic(topic);
						System.out.println("je suis le topic "+topic+"le min est "+min_offset);
						
						/*supprimer les elements de Memoire*/
						topicDataService.deleteDataMem(topic,min_offset);
						}
						
					}
				

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
		
}		
		

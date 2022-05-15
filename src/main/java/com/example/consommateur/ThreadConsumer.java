package com.example.consommateur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.service.DataConfigService;
import com.example.service.TopicDataService;
import com.example.Serveur.ServerMetier;

@Component
@Scope("prototype")
public class ThreadConsumer extends Thread{

public ThreadConsumer() {
		super();
	}

private String adresse_client;
private Socket cannaux;
private int Id_client; 
private ObjectOutputStream out;
public ObjectOutputStream getOut() {
	return out;
}

public void setOut(ObjectOutputStream out) {
	this.out = out;
}

@Autowired
private TopicDataService topicDataService;
@Autowired
private DataConfigService dataConfigService;

	public int getId_client() {
	return Id_client;
}

public void setId_client(int id_client) {
	Id_client = id_client;
}


public String getAdresse_client() {
	return adresse_client;
}

public void setAdresse_client(String adresse_client) {
	this.adresse_client = adresse_client;
}

public Socket getCannaux() {
	return cannaux;
}

public void setCannaux(Socket cannaux) {
	this.cannaux = cannaux;
}

	public ThreadConsumer(String adresse_client,int Id_client , Socket cannaux) 
	{
		
	}
	
	public void run()
	{
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("je suis le consumer de cleint"+Id_client+" : "+cannaux.getInetAddress());
			
			List<String> data;
			CheckOffsetMin(Id_client);//remplacer les offset si sont petit a celle de configuration
			HashMap<String, Integer> map=ServerMetier.Topic_Offset.get(Id_client);
			try {
			while(true) {
			
				for(Map.Entry m:map.entrySet()) 
				{
					
				
				if(ServerMetier.ACK_consummer.get(Id_client).contains("ACK_"+m.getKey())) //le ACK est bien recu
					{
					if(topicDataService.map_data.get(m.getKey()).containsKey(m.getValue())) 
						{
						//enleve le ACK de ce topic
						ServerMetier.ACK_consummer.get(Id_client).remove("ACK_"+m.getKey());
						//envoi la data dans une List   (topic+donne)
						data=new ArrayList<String>();
						data.add((String) m.getKey());
						
						data.add(topicDataService.map_data.get(m.getKey()).get(m.getValue()));
						out.writeObject(data);
					
						
						System.out.println("offset "+m.getValue()+data);
					
						}
			}
				else {Thread.sleep(1000);}
				
			}}
			}
			catch (IOException|InterruptedException e) {
				System.out.println("probleme dans le ThreadConsummer");
				
			}  
	}
	private void CheckOffsetMin(int Id_client) {
		for (Entry<String, Integer> entry : ServerMetier.Topic_Offset.get(Id_client).entrySet()) {
			String key = entry.getKey();
			Integer val = entry.getValue();
			
			if (val<Integer.parseInt(ServerMetier.OffsetMini.get(key))) {
				ServerMetier.Topic_Offset.get(Id_client).replace(key,Integer.parseInt(ServerMetier.OffsetMini.get(key)));
			}
			
		}
	}
	
	
}

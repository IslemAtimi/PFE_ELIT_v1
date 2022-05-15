/*cette classe pour les client producteur donc elle va stocker dans une liste*/

package com.example.producteur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Serveur.ServerMetier;
import com.example.Threads.ThreadS;
import com.example.model.User;
import com.example.service.AuthentificationService;
import com.example.service.TopicDataService;

@Service
public class ProducteurMetier {
	
	@Autowired
	private AuthentificationService authenservice;
	@Autowired
	private TopicDataService topicDataService;
	@Autowired
	private ServerMetier serverMetier;
	
	public synchronized void produce(ObjectOutputStream out,Socket server,String topic,String Data) throws ClassNotFoundException, IOException, InterruptedException {

		String client="producteur";	

		
	
        // receve par le client (prodycteur)

		topicDataService.map_data.get(topic).put(Integer.parseInt(ServerMetier.OffsetMaxi.get(topic)), Data);//ajouter la donnes dans la rama
		int i=Integer.parseInt(ServerMetier.OffsetMaxi.get(topic));
		i++;
		ServerMetier.OffsetMaxi.replace(topic, Integer.toString(i));
        System.out.print("Server receve << client send "+server.getInetAddress()+":"+server.getPort()+">>"+Data+"\n");
		
 

			
	
	}
	

}



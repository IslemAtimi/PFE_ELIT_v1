/* cette classe pour les consommateur donc il faut preparer les donnees*/

package com.example.consommateur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.*;
import com.example.service.AuthentificationService;
import com.example.service.TopicDataService;

@Service
public class ConsommateurMetier {
	
	@Autowired
	AuthentificationService authentificationService;
	@Autowired
	TopicDataService topicDataService; 
	
	public void consome(ObjectOutputStream out,ObjectInputStream in,Socket server,String topic,int Offset) throws ClassNotFoundException, IOException, InterruptedException {
		String line;
		String client="consommateur";	
		int offset=Offset;
		
		
	while (true) {
		
        // send to client (consommateur)
        Thread.sleep(3000);
        LocalTime deb = LocalTime.now();
		out.writeObject(topicDataService.map_data.get(topic).get(offset));
		LocalTime fin = LocalTime.now();
		System.out.println("la taille de la donnees"+topicDataService.map_data.get(topic).get(offset).length());
		line=(String) in.readObject();
		
		System.out.println(line+" dans "+ChronoUnit.NANOS.between(deb, fin)+ " NanoSeconde");
		// If ServerMetier send QUIT (To end conversation).
        if (!line.equals("ACK_RECU")) break;
        if (!line.equals("Quit")) {
        				System.out.println("fait "+offset);
        				offset++;
        				}
        else {
        	out.writeObject("ACK_QUIT");
        	break;
        }
		}
	
        //userService.saveOffset(server.getInetAddress(), topic, offset);//remplacer le nouveaux offset offset+i
		
	}

	
}

package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.TopicData;
import com.example.model.packet.PacketTopic;
import com.example.service.TopicDataService;

@CrossOrigin("*")
@RestController
public class TopicDataController {

@Autowired
private TopicDataService topicDataService;
	
/*ajouter la taille a affiche*/
@RequestMapping(method = RequestMethod.POST ,value ="/TopicData/show")
public List<TopicData> getDataTopic(@RequestBody PacketTopic packetTopic){
	return topicDataService.getAllData(packetTopic.getTopic());		
		}

@RequestMapping(method = RequestMethod.POST ,value ="/TopiName/AddTopic")
public void AddTopic(@RequestBody PacketTopic packetTopic){
	topicDataService.AddTopic(packetTopic.getTopic());
		}

@RequestMapping(method = RequestMethod.POST ,value ="/TopicData/upload")//charger les donnes apres la creation de topic
public void UploadData(@RequestBody PacketTopic packetTopic){
	topicDataService.UploadData(packetTopic.getTopic(), packetTopic.getUserCreate(),packetTopic.getTaille());
		}

@RequestMapping(method = RequestMethod.POST ,value ="/TopicData/delete")
public void deleteAllData(@RequestBody PacketTopic packetTopic){
	topicDataService.deleteData(packetTopic.getTopic());
		}

@RequestMapping(method = RequestMethod.GET ,value ="/TopicName/show")
public List<String> TopicShowName(){
	return topicDataService.listTopic();
		}
}





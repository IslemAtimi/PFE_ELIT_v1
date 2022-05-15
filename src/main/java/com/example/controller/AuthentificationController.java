package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.TopicData;
import com.example.model.packet.PacketPoste;
import com.example.model.packet.PacketTopic;
import com.example.service.AuthentificationService;

@CrossOrigin("*")
@RestController
public class AuthentificationController {

	@Autowired
	private AuthentificationService authentificationService;
	
	@RequestMapping(method = RequestMethod.POST ,value ="/poste/add")
	public void addPoste(@RequestBody PacketPoste packetPoste){
		 authentificationService.addPoste(packetPoste.getAdresse(), packetPoste.getCle());		
			}
	
	@RequestMapping(method = RequestMethod.POST ,value ="/poste/remove")
	public void removePoste(@RequestBody PacketPoste packetPoste){
		 authentificationService.removePoste(packetPoste.getAdresse());		
			}
}

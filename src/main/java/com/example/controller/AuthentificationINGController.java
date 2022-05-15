package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.AuthentificationINGService;
import com.example.model.AuthentificationING;
import com.example.model.packet.PacketING;
import com.example.model.packet.PacketTopic;

@CrossOrigin(origins = "*")
@RestController
public class AuthentificationINGController {
	
	@Autowired
	private AuthentificationINGService authentificationINGService;
	
	@RequestMapping(method = RequestMethod.GET ,value ="/AuthentificationING/show")
	public List<AuthentificationING> getING(){
		return authentificationINGService.getAll();
	}
	@RequestMapping(method = RequestMethod.POST ,value ="/AuthentificationING/id")
	public Optional<AuthentificationING> getINGById(@RequestBody PacketING packetING){
		return authentificationINGService.getINGById(packetING.getId());
	}
	@RequestMapping(method = RequestMethod.POST ,value ="/AuthentificationING/name")
	public AuthentificationING getINGByName(@RequestBody PacketING packetING){
		return authentificationINGService.getINGByName(packetING.getName());
	}
	@RequestMapping(method = RequestMethod.POST ,value ="/AuthentificationING/add")
	public void AddING(@RequestBody PacketING packetING){
		authentificationINGService.AddIngeniere(packetING.getId(), packetING.getName(), packetING.getPassword());
	}
	/*--------------------*/
	@RequestMapping(method = RequestMethod.POST ,value ="/AuthentificationING/connect")
	public Response connect(@RequestBody PacketING packetING){
		authentificationINGService.AddIngeniere(packetING.getId(), packetING.getName(), packetING.getPassword());
		return null;
	}
	

}

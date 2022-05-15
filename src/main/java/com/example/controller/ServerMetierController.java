package com.example.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.Serveur.ServerMetier;
import com.example.model.packet.PacketPoste;

@CrossOrigin("*")
@RestController
public class ServerMetierController {

	@Autowired
	private ServerMetier serverMetier;
	
	@RequestMapping(method = RequestMethod.GET ,value ="/server/run")
	public void lancerServer() throws IOException{
		 serverMetier.lancer();		
			}
	@RequestMapping(method = RequestMethod.GET ,value ="/server/stop")
	public void StopServer() throws IOException{
		 serverMetier.stop_serveur();		
			}
	
}

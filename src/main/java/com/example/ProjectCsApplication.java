package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.example.Serveur.ServerMetier;
import com.example.model.TopicData;
import com.example.repository.TopicDataRepo;
import com.example.service.DataConfigService;
import com.example.service.TopicDataService;
import com.example.service.UserService;


@SpringBootApplication

public class ProjectCsApplication  implements CommandLineRunner{

	@Autowired
	private ServerMetier ServerMetier;
	@Autowired
	private DataConfigService dataConfigService;

	public static void main(String[] args){
		SpringApplication.run(ProjectCsApplication.class, args);
	
	}
	//redirege le lancement vers les threads
	@Override
    public void run(String... args) throws Exception {

      
    System.out.println("lunch programme of thread");
   
     
    
    
   //ServerMetier.lancer();
    
    }

}

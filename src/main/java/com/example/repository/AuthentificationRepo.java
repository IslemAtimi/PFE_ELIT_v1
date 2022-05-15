package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.model.Authentification;
import com.mongodb.client.MongoDatabase;

public interface AuthentificationRepo extends MongoRepository<Authentification, Long>{
	
	Authentification findByAdresse(String adresse);

	Authentification findFirstByOrderByIdDesc();
}

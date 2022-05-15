package com.example.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.AuthentificationING;

public interface AuthentificationINGRepo extends MongoRepository<AuthentificationING, String>{
	
	AuthentificationING findByUserName(String userName);

	AuthentificationING findFirstByOrderByIdDesc();
}

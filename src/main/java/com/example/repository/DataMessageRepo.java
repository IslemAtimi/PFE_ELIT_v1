package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.Authentification;
import com.example.model.User;


public interface DataMessageRepo extends MongoRepository<User, String>{
	
	User findByTopic(String topic);

}

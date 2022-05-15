package com.example.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.Authentification;
import com.example.model.User;


public interface UserRepo extends MongoRepository<User, String>{

	User findByid(String id);

	User findFirstByOrderByIdDesc();

}

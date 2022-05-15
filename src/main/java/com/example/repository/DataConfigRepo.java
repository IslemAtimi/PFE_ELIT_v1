package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.DataConfig;
import com.example.model.User;

public interface DataConfigRepo extends MongoRepository<DataConfig, Long>{
	
	

}

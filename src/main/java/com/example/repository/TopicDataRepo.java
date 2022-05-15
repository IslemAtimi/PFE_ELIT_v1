package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.User;

public interface TopicDataRepo extends MongoRepository<User, Long>, MongoTopicDataRepo{

}

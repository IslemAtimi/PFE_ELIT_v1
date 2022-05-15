package com.example.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.repository.AuthentificationRepo;

@Document(collection="User")
public class User {
	
	@Id
	private String id;
	private HashMap<String, Integer> Topic;
	private String groupId;
	
	
	public User() {
		super();
	}
	public User(String id, HashMap<String, Integer> topic, String groupId) {
		super();
		this.id = id;
		Topic = topic;
		this.groupId = groupId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public HashMap<String, Integer> getTopic() {
		return Topic;
	}
	public void setTopic(HashMap<String, Integer> topic) {
		Topic = topic;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	
}
	
	
	
	
	
	
	
	
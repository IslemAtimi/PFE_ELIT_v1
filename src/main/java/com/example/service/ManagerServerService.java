package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.TopicData;
import com.example.repository.TopicDataRepo;

@Service
public class ManagerServerService {
@Autowired
private TopicDataRepo topicDataRepo; 
	public void stoppedOperation() {
		/**/
	}
	public void stockDataMongo(String topic) {
		System.out.println("stock in MongoDB");
		topicDataRepo.deleteAllData(topic);
		
	}
}

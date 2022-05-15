package com.example.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

import com.example.model.TopicData;

public class TopicDataRepoImpl implements MongoTopicDataRepo{
	
	@Autowired
	MongoOperations mongoOperations;
	@Value("${CollectionsConfig}")
	String configList;
	
	public List<TopicData> getAll(String collection){
		return mongoOperations.find(new Query(), TopicData.class, collection);
	}
	public List<TopicData> getIdIntervall(String collection,int id_inf,int id_sup){
		Query query = new Query();
		query.addCriteria(Criteria.where("id").gt(id_sup).lt(id_inf));
		return mongoOperations.find(query, TopicData.class, collection);
	}
	public List<TopicData> getById(String collection,Long id){
		return mongoOperations.find(new Query(), TopicData.class, collection);
	}
	public void saveData(String collection,TopicData data) {
		mongoOperations.save(data, collection);
	}
	public void saveAllData(String collection,List<TopicData> data) {
		//mongoOperations.save(mongoOperations.insertAll(data), collection); 
		mongoOperations.insert(data, collection);
	}
	public void createCollection_ifNotExist(String collection) {
		  Assert.notNull(collection);
		  if (!mongoOperations.collectionExists(collection)) {
			  mongoOperations.createCollection(collection);
		  }
	}
	public boolean deleteData(int id,String collection) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").gt(id));
		mongoOperations.remove(query, collection);
		return false;
	}
	public void deleteAllData(String collection) {
		Query query = new Query();
		query.addCriteria(Criteria.where("topic").is(collection));
		mongoOperations.remove(query, collection);
	}
	//liste des topic dans Mongo
	public List<String> listTopic(){
		List<String> L1=new ArrayList<String>();
		
		for(String s:mongoOperations.getCollectionNames()) {
			if(!configList.contains(s))
			L1.add(s);
		}
		
		return L1;
	}
	
	
}



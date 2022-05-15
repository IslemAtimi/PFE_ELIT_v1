package com.example.repository;

import java.util.List;

import com.example.model.TopicData;

public interface MongoTopicDataRepo {
	public List<TopicData> getAll(String collection);
	public List<TopicData> getById(String collection,Long id);
	public void saveData(String collection,TopicData data);
	public void saveAllData(String collection,List<TopicData> data);
	public void createCollection_ifNotExist(String collection);
	public boolean deleteData(int id,String collection);
	public List<TopicData> getIdIntervall(String collection,int id_inf,int id_sup);
	public void deleteAllData(String collection);
	public List<String> listTopic();
}

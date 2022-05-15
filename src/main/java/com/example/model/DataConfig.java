package com.example.model;

import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("DataConfiguration")
public class DataConfig {
	@Id
	private Long id;
	private HashMap<String, String> OffsetMin;
	private String Max_Thread;
	private String CollectionsConfig;
	private String MAX_Time_Sleep;
	private String MAX_Data;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public HashMap<String, String> getOffsetMin() {
		return OffsetMin;
	}
	public void setOffsetMin(HashMap<String, String> offsetMin) {
		OffsetMin = offsetMin;
	}
	public String getMax_Thread() {
		return Max_Thread;
	}
	public void setMax_Thread(String max_Thread) {
		Max_Thread = max_Thread;
	}
	public String getCollectionsConfig() {
		return CollectionsConfig;
	}
	public void setCollectionsConfig(String collectionsConfig) {
		CollectionsConfig = collectionsConfig;
	}
	public String getMAX_Time_Sleep() {
		return MAX_Time_Sleep;
	}
	public void setMAX_Time_Sleep(String mAX_Time_Sleep) {
		MAX_Time_Sleep = mAX_Time_Sleep;
	}
	public String getMAX_Data() {
		return MAX_Data;
	}
	public void setMAX_Data(String mAX_Data) {
		MAX_Data = mAX_Data;
	}
	public DataConfig() {
		super();
	}
	
	
}

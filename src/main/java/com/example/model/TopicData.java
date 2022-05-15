package com.example.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TopicData {
	
		@Id
		private Long id;
		private String topic;
		private String data;
		private String client_create;
		private Date date_creation; 
		
		public TopicData() {
			super();
		}

		public TopicData(Long id, String topic, String data, String client_create, Date date_create) {
			super();
			this.id = id;
			this.topic = topic;
			this.data = data;
			this.client_create = client_create;
			this.date_creation = date_create;
		}



		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getTopic() {
			return topic;
		}

		public void setTopic(String topic) {
			this.topic = topic;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public String getClient_create() {
			return client_create;
		}

		public void setClient_create(String client_create) {
			this.client_create = client_create;
		}

		public Date getDate_create() {
			return date_creation;
		}

		public void setDate_create(Date date_create) {
			this.date_creation = date_create;
		}


	}
		
		
		
		
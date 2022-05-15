package com.example.model.packet;


public class PacketTopic {
@Override
	public String toString() {
		return "PacketTopic [topic=" + topic + ", taille=" + taille + ", userCreate=" + userCreate + "]";
	}
private String topic;
private String taille;
private String userCreate;


public String getTopic() {
	return topic;
}
public void setTopic(String topic) {
	this.topic = topic;
}
public String getTaille() {
	return taille;
}
public void setTaille(String taille) {
	this.taille = taille;
}
public String getUserCreate() {
	return userCreate;
}
public void setUserCreate(String userCreate) {
	this.userCreate = userCreate;
}
}

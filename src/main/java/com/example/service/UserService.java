package com.example.service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.management.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Serveur.ServerMetier;
import com.example.model.Authentification;
import com.example.model.User;
import com.example.repository.UserRepo;

@Service
public class UserService {

	public UserService()
	{
		System.out.println("Class UserService cree");
	}	
	@Autowired
	 private UserRepo userRepo;
	
	//return tt les postes
	public List<User> getall()
	{
		return userRepo.findAll();		
	}
	
	//ajouter un poste avec adresse et cle
		public void addUser(List<String> topics) {
			//db.collection.find().sort({age:-1}).limit(1) // for MAX
			User user_max=userRepo.findFirstByOrderByIdDesc();
			
			HashMap<String, Integer> map=new HashMap<String, Integer>();
			for(String s:topics) {
				map.put(s, 0);
			}
			User user = new User();
			user.setId("client_"+(Integer.parseInt(user_max.getId().split("_")[1])+1));	
			user.setTopic(map);
			user.setGroupId("id_standard");
			userRepo.save(user);
			
			}
		
		//methode de recuperation des topic avec offset
		public HashMap<String, Integer> getTopicOffset(String id_user){
			User user=userRepo.findByid(id_user);
			return user.getTopic();
		}
		
		//methide de recuperation des topic d'un client
		public List<String> getTopicClient(String id_user){
			
			List<String> list=new ArrayList<String>();
			User user=userRepo.findByid(id_user);
			HashMap<String, Integer> map=user.getTopic();
			for(Map.Entry m : map.entrySet()) {
				list.add((String) m.getKey());
			}
		return list;
		}
		
		/*recuperer le offset d'un topic donne*/
		public int getOffset(String id_user,String topic) {
			try {
			
			User user=userRepo.findByid(id_user);
			int offset=user.getTopic().get(topic);
			return offset;
			}catch (Exception e) {
				System.out.println("aucun client nexiste dans ce topic");// TODO: handle exception
				return -1;
			}	
		}
		//save un seul offset dun topic
		public void saveOffset(String id_user,String topic,int offset) {
			
			User user=userRepo.findByid(id_user);
			user.getTopic().replace(topic, offset);
			userRepo.save(user);
		}
		
		//sauvgarder les offset dun client
			public void saveOffsetClient(int IdClient,String id_user) {
				HashMap<String, Integer> map=ServerMetier.Topic_Offset.get(IdClient);
				
				User user=userRepo.findByid(id_user);
				user.setTopic(map);
				userRepo.save(user);
			}
		
}

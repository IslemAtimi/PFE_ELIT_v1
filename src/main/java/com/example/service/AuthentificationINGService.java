package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AuthentificationINGRepo;
import com.example.model.AuthentificationING;

@Service
public class AuthentificationINGService {

	@Autowired
	private AuthentificationINGRepo authentificationINGRepo;
	
	public AuthentificationINGService() {
		
	}
	
	public List<AuthentificationING> getAll(){
		return authentificationINGRepo.findAll();
	}
	
	public void AddIngeniere(String id,String UserName,String Password) {
		AuthentificationING user=new AuthentificationING();
		user.setId(id);
		user.setUserName(UserName);
		user.setPassword(Password);
		authentificationINGRepo.save(user);
	}

	public Optional<AuthentificationING> getINGById(String id) {
		
		return authentificationINGRepo.findById(id);	
	}
	public AuthentificationING getINGByName(String name) {

		return authentificationINGRepo.findByUserName(name);	
	}
	
}

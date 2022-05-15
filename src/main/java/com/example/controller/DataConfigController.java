package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.AuthentificationING;
import com.example.model.DataConfig;
import com.example.service.DataConfigService;

@CrossOrigin("*")
@RestController
public class DataConfigController {
	
	@Autowired
	private DataConfigService dataConfigService;
	
	@RequestMapping(method = RequestMethod.GET ,value ="/configuration/show{id}")
	public Optional<DataConfig> getConfigLast(@PathVariable String id){
		return dataConfigService.getConfig(Integer.parseInt(id));
	}
	/*ajouter les enter*/
	@RequestMapping(method = RequestMethod.GET ,value ="/configuration/add")
	public void ajoutconfig(){
		dataConfigService.createConfig((long) 0, "20", "5", "5");
	}

}

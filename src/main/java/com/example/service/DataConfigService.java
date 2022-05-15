package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.DataConfig;
import com.example.repository.DataConfigRepo;
import com.example.Serveur.ServerMetier;

@Service
public class DataConfigService {

	@Autowired
	private DataConfigRepo dataConfigRepo;
	@Autowired
	private TopicDataService topicDataService;
	
	
	
	public void createConfig(Long id,String MaxData,String MaxClient,String MaxTime) {
		DataConfig conf=new DataConfig();
		HashMap<String, String> map=new HashMap<String, String>();
		List<String> topics=topicDataService.listTopic();
		
		for(String s:topics) {
			map.put(s, "0");
		}
		conf.setId(id);
		conf.setOffsetMin(map);
		conf.setMAX_Data(MaxData);
		conf.setMax_Thread(MaxClient);
		conf.setMAX_Time_Sleep(MaxTime);
		conf.setCollectionsConfig("AuthentificationING,User,authentification");
		dataConfigRepo.save(conf);
		
	}
	public Optional<DataConfig> getConfig(int id) {
		return dataConfigRepo.findById((long)id);
	}
	
	public void chargerConfig(int id) {
		Optional<DataConfig> conf=dataConfigRepo.findById((long) id);
		ServerMetier.OffsetMini=conf.get().getOffsetMin();
		ServerMetier.OffsetMaxi=ServerMetier.OffsetMini;
		ServerMetier.Max_Thread=Integer.parseInt(conf.get().getMax_Thread());
		ServerMetier.MAX_Time_Sleep=Integer.parseInt(conf.get().getMAX_Time_Sleep());
		ServerMetier.MAX_Data=Integer.parseInt(conf.get().getMAX_Data());
		ServerMetier.CollectionsConfig=conf.get().getCollectionsConfig();
		
		
		
	}
	public void saveOffsetMini(int id) {
		Optional<DataConfig> conf=dataConfigRepo.findById((long) id);
		
		for (Entry<String, String> map : ServerMetier.OffsetMaxi.entrySet()) {
		
			String key = map.getKey();
			String val = map.getValue();
		if((Integer.parseInt(val)-ServerMetier.MAX_Data)>Integer.parseInt(ServerMetier.OffsetMini.get(key))) {	
		ServerMetier.OffsetMini.replace(key, Integer.toString(Integer.parseInt(val)-ServerMetier.MAX_Data));
		}
		}
		conf.get().setOffsetMin(ServerMetier.OffsetMini);
		dataConfigRepo.save(conf.get());
	}
}

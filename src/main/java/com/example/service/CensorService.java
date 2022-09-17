package com.example.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Censor;
import com.example.repository.CensorRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CensorService {

	@Autowired
	CensorRepository censorRepository;

	public List<Censor> getAllCensor() {
		return censorRepository.findAll();
	}

	public Map<String, Object> findCensorById(String id) {
		Censor censor = censorRepository.findBy_id(id);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.convertValue(censor, new TypeReference<Map<String, Object>>() {
		});

		return map;
	}

	public Censor updateCensor(String id) {
		Censor censor = censorRepository.findBy_id(id);
		censor.setLang("New Lang");

		return censorRepository.save(censor);

	}

}

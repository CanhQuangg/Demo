package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Censor;
import com.example.service.CensorService;

@RestController
@RequestMapping("/censor")
public class CensorController {
	private final CensorService censorService;

	@Autowired
	public CensorController(CensorService censorService) {
		this.censorService = censorService;
	}

	@GetMapping("/all")
	public List<Censor> getAllCensor() {
		return censorService.getAllCensor();
	}

	@GetMapping("/{id}")
	public Map<String, Object> getById(@PathVariable(name = "id") String id) {
		return censorService.findCensorById(id);
	}

	@PutMapping("/{id}")
	public Censor updateCensor(@PathVariable(name = "id") String id) {
		return (Censor) censorService.updateCensor(id);
	}
}

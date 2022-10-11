package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.ResponseObject;
import com.example.entity.Test;
import com.example.repository.TestRepository;
import com.example.service.TestService;

@RestController
@RequestMapping("/test2")
public class Test2Controller {

	@Autowired
	TestRepository repository;

	@Autowired
	TestService service;

	@GetMapping("/all")
	ResponseEntity<ResponseObject> getAllTest2() {
		List<Test> data = repository.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", "Get all Test", data));
	}

	@GetMapping("/{id}")
	ResponseEntity<ResponseObject> getTest2(@PathVariable(name = "id") String id) {

		Optional<Test> test = repository.findById(id);
		if (test.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", "Find test by id", test));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject("404", "Cannot find test by that id", ""));
		}
	}

	@DeleteMapping("/{id}")
	ResponseEntity<ResponseObject> deleteTest(@PathVariable(name = "id") String id) {
		Optional<Test> test = repository.findById(id);
		if (test.isPresent()) {
			service.deleteTestById(id);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200", "Deleted Test by id " + id, ""));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseObject("404", "Cannot find test by that id", ""));
		}
	}
}

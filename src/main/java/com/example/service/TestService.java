package com.example.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Test;
import com.example.repository.TestRepository;

@Service
public class TestService {
	@Autowired
	TestRepository testRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(TestService.class);

	public CompletableFuture<List<Test>> getAll() {
		LOGGER.info("get all test");
		List<Test> tests = testRepository.findAll();
		return CompletableFuture.completedFuture(tests);
	}

//	public Test addNewTest() {
//		LOGGER.info("create new test");
//		Query query = new Query();
//		String content = "Quang " + (mongoTemplate.count(query, "test") + 1);
//		Test newTest = new Test(content);
//		return mongoTemplate.insert(newTest, "test");
//	}

	@Async
	@Transactional(rollbackFor = Exception.class)
	public CompletableFuture<Test> addNewTest() {
		LOGGER.info("create new test with thread - " + Thread.currentThread().getName());
		Query query = new Query();
		String content = "Quang " + (mongoTemplate.count(query, "test") + 1);
		Test newTest = new Test(content);
		LOGGER.info("create new test with thread - " + Thread.currentThread().getName() + " - done");
		return CompletableFuture.completedFuture(mongoTemplate.insert(newTest, "test"));
	}

	public void deleteTestById(String id) {
		testRepository.deleteById(id);
	}

}

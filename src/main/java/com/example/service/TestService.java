package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Test;
import com.example.repository.TestRepository;
import com.mongodb.MongoException;

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
		List<Test> data = new ArrayList<>();
		for (int i = tests.size() - 2; i < tests.size(); i++) {
			data.add(tests.get(i));
		}
		return CompletableFuture.completedFuture(data);
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

	public Test getTestById(String id) {
		try {
			Optional<Test> test = testRepository.findById(id);
			if (test.isPresent()) {
				return mongoTemplate.findById(id, Test.class);
			} else {
				return null;
			}
		} catch (MongoException e) {
			LOGGER.info("Error: " + e);
			return null;
		}
	}

	// Phân trang khi lấy tất cả các test
	public void getTestPage() {
		List<Test> data = mongoTemplate.findAll(Test.class);
		for (int i = 0; i <= (data.size() / 5); i++) {
			Query query = new Query().with(Sort.by(Sort.Order.asc("name"))).limit(5).skip(i * 5);
			List<Test> output = mongoTemplate.find(query, Test.class);
			output.forEach(System.out::println);
			System.out.println("============");
		}
	}

	// upsert Test
	// https://stackoverflow.com/questions/35220287/upsert-mongo-document-using-spring-data-mongo
//	public Test upsertTestById(String id, Test test) {
//		try {
//			Optional<Test> data = testRepository.findById(id);
//			if (data.isPresent()) {
//
//				return null;
//			}
//		} catch (MongoException e) {
//			LOGGER.info("Error: " + e);
//			return null;
//		}
//	}
}

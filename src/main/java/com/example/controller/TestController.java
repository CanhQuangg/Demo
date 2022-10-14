package com.example.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.config.TaskExecutor;
import com.example.entity.Post;
import com.example.entity.Test;
import com.example.service.PostService;
import com.example.service.TestService;

@RestController
@RequestMapping("/test")
public class TestController {
	private final TestService testService;

	@Autowired
	private PostService postService;

	@Autowired
	TaskExecutor executor;

	@Autowired
	public TestController(TestService testService) {
		this.testService = testService;
	}

	@GetMapping("/all")
	public CompletableFuture<List<Test>> getAll() {
		return testService.getAll();
	}

	@PostMapping("/add")
	@Transactional(rollbackFor = Exception.class)
	// đang bị tạo 2 test có cùng số
	public String createNewTest() throws InterruptedException, ExecutionException {
		CompletableFuture<Test> createTest = testService.addNewTest();
		CompletableFuture<Test> createTest1 = testService.addNewTest();
		CompletableFuture<Post> createPost = postService.addNewPost();

//		CompletableFuture.allOf(testService.addNewTest(), testService.addNewTest(), postService.addNewPost()).join();
		return new String("done");
	}
}

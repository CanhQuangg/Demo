package com.example.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Post;
import com.example.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
//	@Autowired
	private final PostService postService;

	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping("/all")
	public List<Post> getAllPost() {
		return postService.getAllPost();
	}

	// Get posts @async
	@GetMapping("/get")
	public CompletableFuture<List<Post>> getPosts() {
		return postService.getPosts();
	}

	@PostMapping("/add")
	public String createNewPost() {
		postService.addNewPost();
		return new String("done");
//		return postService.addNewPost();
	}
}

package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@PostMapping("/add")
	public Post createNewPost(@RequestBody Post newPost) {
		return postService.addNewPost(newPost);
	}
}

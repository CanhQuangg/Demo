package com.example.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.entity.Post;
import com.example.repository.PostRepository;

@Service("postService")
public class PostService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);
	@Autowired
	PostRepository postRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	public List<Post> getAllPost() {
		LOGGER.info("Getting all Post");
		return mongoTemplate.findAll(Post.class);
	}

	public Post addNewPost(Post newPost) {
		return mongoTemplate.insert(newPost, "post");
	}

	@Async
	public CompletableFuture<Post> createPost(Post newPost) {
		LOGGER.info("New post is saving");
		return CompletableFuture.completedFuture(mongoTemplate.insert(newPost, "post"));
	}
}

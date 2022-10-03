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

	public CompletableFuture<List<Post>> getPosts() {
		LOGGER.info("get all @async test");
		List<Post> posts = postRepository.findAll();
		return CompletableFuture.completedFuture(posts);
	}

//	public Post addNewPost() {
//		LOGGER.info("creating new post");
//		Query query = new Query();
//		String content = "Post " + (mongoTemplate.count(query, "post") + 1);
//		Post newPost = new Post("Canh Quang", "post", content);
//		return mongoTemplate.insert(newPost, "post");
//	}

	@Async
	@Transactional(rollbackFor = Exception.class)
	public CompletableFuture<Post> addNewPost() {
		LOGGER.info("creating new post with thread - " + Thread.currentThread().getName());
		Query query = new Query();
		String content = "Post " + (mongoTemplate.count(query, "post") + 1);
		Post newPost = new Post("Canh Quang", "post", content);
		LOGGER.info("new post with thread - " + Thread.currentThread().getName() + " - Done");
		return CompletableFuture.completedFuture(mongoTemplate.insert(newPost, "post"));
	}
}

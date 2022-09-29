package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.example.entity.Post;
import com.example.repository.PostRepository;

@Service("postService")
public class PostService {
	@Autowired
	PostRepository postRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	public List<Post> getAllPost() {
		return mongoTemplate.findAll(Post.class);
	}

	public Post addNewPost(Post newPost) {
		return mongoTemplate.insert(newPost, "post");
//		return postRepository.save(newPost);
	}
}

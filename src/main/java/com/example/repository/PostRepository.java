package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.entity.Post;

public interface PostRepository extends MongoRepository<Post, String> {

}

package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

}

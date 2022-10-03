package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Test;

@Repository
public interface TestRepository extends MongoRepository<Test, String> {

}

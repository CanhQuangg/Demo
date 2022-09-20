package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.entity.Censor2;

public interface Censor2Repository extends MongoRepository<Censor2, String> {
	Censor2 findBy_id(String _id);
}

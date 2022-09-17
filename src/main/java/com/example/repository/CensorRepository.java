package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.entity.Censor;

public interface CensorRepository extends MongoRepository<Censor, String> {

	public Censor findBy_id(String _id);

}

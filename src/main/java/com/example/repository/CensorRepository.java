package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Censor;

@Repository
public interface CensorRepository extends MongoRepository<Censor, String> {

	Censor findBy_id(String _id);
}

package com.example.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.entity.Censor;

public interface CensorRepository extends MongoRepository<Censor, String> {

	Censor findBy_id(String _id);

	@Query("{level: {$gt: 2}}")
	List<Censor> findCensorLevelGreaterThan2();

}

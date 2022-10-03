package com.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "test")
@Data
public class Test {
	@Id
	private String _id;
	private String name;

	public Test(String name) {
		this.name = name;
	}

}

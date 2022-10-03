package com.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "post")
@Data
public class Post {
	@Id
	private String _id;
	private String user;
	private String title;
	private String content;

	public Post(String user, String title, String content) {
		this.user = user;
		this.title = title;
		this.content = content;
	}

}

package com.example.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class CensorDtoTest {
	private String _id;
	private String scope;
	private LocalDateTime when;
	private String content;
}

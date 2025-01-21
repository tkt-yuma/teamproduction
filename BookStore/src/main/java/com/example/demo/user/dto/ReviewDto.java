package com.example.demo.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class ReviewDto {
	
	@NotEmpty(message = "本文を入力してください")
	@Size(max = 1000, message = "本文は1000文字以内で入力してください")
	private String review;
	
}

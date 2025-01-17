package com.example.demo.admin.dto;

import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class AdminDto {
	
	@Size(max = 100 )
	private String quantity;

}

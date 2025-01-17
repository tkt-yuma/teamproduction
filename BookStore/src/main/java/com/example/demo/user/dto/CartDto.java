package com.example.demo.user.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class CartDto implements Serializable{
	
	@Size(max = 100 )
	private int quantity;

}

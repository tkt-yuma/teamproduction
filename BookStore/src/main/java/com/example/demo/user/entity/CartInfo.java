package com.example.demo.user.entity;

import lombok.Data;

@Data
public class CartInfo {
	private Integer num;
	private Integer userId ;
	private Integer itemId ;
	private String itemName ;
	private Integer quantity;
	private ItemId itemIdDetails;
	
}

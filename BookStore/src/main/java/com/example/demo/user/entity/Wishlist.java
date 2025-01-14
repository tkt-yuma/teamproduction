package com.example.demo.user.entity;

import lombok.Data;

@Data
public class Wishlist {
	private Integer wishListNo;
	private Integer itemId;
	private String itemName;
	private Integer userId;

}

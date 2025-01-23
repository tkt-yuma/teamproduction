package com.example.demo.user.entity;

import lombok.Data;

@Data
public class ItemId {
	private Integer itemId;
	private String itemName;
	private String categoryName;
	private Integer itemPurprice;
	private Integer itemSaleprice;
	private String  iteminfo;
	private Integer stock;
	private String  purchaseno;
	private String imageaddress;
}

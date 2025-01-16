package com.example.demo.user.entity;

import java.util.Date;

import lombok.Data;

@Data
public class SaleManagement {

	private Integer saleNum;
	private Integer itemId;
	private String itemName;
	private Integer itemNum;
	private Integer itemPurprice;
	private Integer itemSaleprice;
	private Date itemSaledate;
	private Integer userId;

}

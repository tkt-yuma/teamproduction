package com.example.demo.admin.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Salemanage {
	private Integer saleNum;
	private Integer itemId;
	private String itemName;
	private Integer itemNum;
	private Integer itemPurprice;
	private Integer itemSaleprice;
	private Date itemSaledate;
	private Integer userId;

}

package com.example.demo.admin.entity;

import lombok.Data;

/*
 * テスト用消してもらってかまいません（小野）
 */
@Data
public class Inventory {
	 private String itemId;
	    private String itemName;
	    private String category;
	    private int purprice;
	    private int saleprice;
	    private String iteminfo;
	    private int stock;
	    private String purchaseno;
	    private String imageaddress;
}

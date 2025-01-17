package com.example.demo.admin.entity;

import java.util.Date;

import lombok.Data;


/*
 * テスト用消してもらってかまいません（小野）
 */
@Data
public class Sales {
	 	private int salenum;
		private Date saleDate;
	 	private String itemId;
	    private String itemName;
	    private int itemnum;
	    private String category;
	    private int purprice;
	    private int saleprice;
	    private int userId;
	    
	

}

package com.example.demo.admin.entity;


import java.util.Date;

import lombok.Data;

@Data
public class PurchaseManage {
	private Integer purchaseNum;
	private Integer itemId;
	private Integer buynum;
	private Integer adminId;
	private Date buyDate;

}

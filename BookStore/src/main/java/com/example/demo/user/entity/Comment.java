package com.example.demo.user.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Comment {
	private Integer num;
	private Integer itemId;
	private String itemName;
	private Integer userId;
	private Date date;
	private String review;

}

package com.example.demo.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SaleManagement {
	List<SaleManagement> selectAll();
}

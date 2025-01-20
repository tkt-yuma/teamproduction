package com.example.demo.user.repository;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.user.entity.CartInfo;
import com.example.demo.user.entity.SaleManagement;

@Mapper
public interface SaleManagementMapper {
	List<SaleManagement> getPurchaseHistoryForUser();

	List<SaleManagement> selectAll();

	void processOrder(CartInfo carts);
}

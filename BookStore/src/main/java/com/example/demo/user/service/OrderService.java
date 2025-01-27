package com.example.demo.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.user.entity.CartInfo;
import com.example.demo.user.entity.SaleManagement;
import com.example.demo.user.repository.SaleManagementMapper;


@Service
public class OrderService {

	@Autowired
	private SaleManagementMapper salemapper;

	public List<SaleManagement> getPurchaseHistoryForUser(Integer userId) {
		// TODO 自動生成されたメソッド・スタブ
		return salemapper.getPurchaseHistoryForUser(userId);
	}

	public void processOrder(List<CartInfo> cart) {
		// TODO 自動生成されたメソッド・スタ
		for(CartInfo carts: cart) {
			salemapper.processOrder(carts);
		}
	}

}

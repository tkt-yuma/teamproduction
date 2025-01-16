package com.example.demo.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.user.entity.CartInfo;
import com.example.demo.user.entity.SellManagement;

@Service
public class OrderService {

	public int getEstimatedDeliveryDays(Integer userId) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	public List<SellManagement> getPurchaseHistoryForUser(Integer userId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public List<CartInfo> getOrderItemsForUser(Integer userId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public void processOrder(Integer userId, String paymentMethod, String cardNumber) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}

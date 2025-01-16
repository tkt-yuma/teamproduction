package com.example.demo.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.user.entity.CartInfo;

@Service
public class CartService {


	public List<CartInfo> getCartItems(Integer currentUserId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public double calculateTotal(Integer currentUserId) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	public void updateItemQuantity(Integer currentUserId, Integer itemId, Integer quantity) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	public void removeItem(Integer currentUserId, Integer itemId) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	public void addToCart(Integer currentUserId, Integer itemId, Integer quantity) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	public void addToCart(Integer currentUserId, CartInfo item) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
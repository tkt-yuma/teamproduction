package com.example.demo.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.user.entity.CartInfo;
import com.example.demo.user.repository.CartInfoMapper;

@Service
public class CartService {

	@Autowired
	private CartInfoMapper cartInfoMapper;
	
	public List<CartInfo> getCartItems(Integer UserId) {
		// TODO 自動生成されたメソッド・スタブ
		return cartInfoMapper.cartById();
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
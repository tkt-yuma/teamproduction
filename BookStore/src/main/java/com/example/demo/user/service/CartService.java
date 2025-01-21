package com.example.demo.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.user.dto.CartDto;
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



	public void addToCart(Integer userId, Integer itemId, CartDto cartDto) {
		// TODO 自動生成されたメソッド・スタブ
		cartInfoMapper.addToCart(userId,itemId,cartDto);
	}

	public void updateItemQuantity(Integer userId, Integer itemId, CartDto cartDto) {
		// TODO 自動生成されたメソッド・スタブ
		cartInfoMapper.updateItemQuantity(userId,itemId,cartDto);
	}

	public void deleteItemCart(Integer userId, Integer itemId) {
		// TODO 自動生成されたメソッド・スタブ
		cartInfoMapper.deleteItemCart(userId,itemId);
	}



	
}
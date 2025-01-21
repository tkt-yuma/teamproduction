package com.example.demo.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.user.entity.Wishlist;
import com.example.demo.user.repository.WishlistMapper;

@Service
public class WantService {

	@Autowired
	private WishlistMapper wishlistMapper;
	
	public void addToWantList(Integer currentUserId, Integer itemId) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	public List<Wishlist> getWantItems(Integer UserId) {
		// TODO 自動生成されたメソッド・スタブ
		return wishlistMapper.getWantItems(UserId);
	}

	


	public void deleteWishList(Integer userId, Integer itemId) {
		// TODO 自動生成されたメソッド・スタブ
		wishlistMapper.deleteWishList(userId,itemId);
	}

}

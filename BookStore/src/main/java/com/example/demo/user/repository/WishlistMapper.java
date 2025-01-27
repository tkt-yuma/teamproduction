package com.example.demo.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.user.entity.Wishlist;
@Mapper
public interface WishlistMapper {
	List<Wishlist> selectAll();

	List<Wishlist> getWantItems(Integer userId);

	void deleteWishList(Integer userId, Integer itemId);
}

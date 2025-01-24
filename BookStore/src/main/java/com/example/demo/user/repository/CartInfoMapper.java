package com.example.demo.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.user.dto.CartDto;
import com.example.demo.user.entity.CartInfo;

@Mapper
public interface CartInfoMapper {
	List<CartInfo> selectAll();

	List<CartInfo> findById(Integer id);

	void addToCart(Integer userId, Integer itemId, CartDto cartDto);

	void updateItemQuantity(Integer userId, Integer itemId, CartDto cartDto);

	void deleteItemCart(Integer userId, Integer itemId);
}

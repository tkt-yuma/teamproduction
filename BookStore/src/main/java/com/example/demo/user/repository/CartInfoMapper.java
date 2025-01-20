package com.example.demo.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.user.entity.CartInfo;

@Mapper
public interface CartInfoMapper {
	List<CartInfo> selectAll();

	List<CartInfo> cartById();
}

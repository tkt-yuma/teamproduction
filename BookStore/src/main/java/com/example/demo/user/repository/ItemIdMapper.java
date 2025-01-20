package com.example.demo.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.user.entity.ItemId;
@Mapper
public interface ItemIdMapper {
	List<ItemId> selectAll();

	List<ItemId> search(String query);

	List<ItemId> searchCategory(String categoryName);

	ItemId searchById(Integer itemId);
}

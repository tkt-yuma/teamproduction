package com.example.demo.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.user.entity.ItemId;
import com.example.demo.user.repository.ItemIdMapper;

@Service
public class BookService {
	@Autowired
	private ItemIdMapper itemIdMapper;


	public  ItemId getItemById(Integer itemId) { 
		// TODO 自動生成されたメソッド・スタブ
		return itemIdMapper.searchById(itemId);
	}

	public List<ItemId> getBooksByCategory(String categoryName) {
		// TODO 自動生成されたメソッド・スタブ
		return itemIdMapper.searchCategory(categoryName);
	}

	public List<ItemId> searchBooks(String query) {
		// TODO 自動生成されたメソッド・スタブ
		return itemIdMapper.search(query);
	}
}

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

	public ItemId getItemById(Integer itemId) { 
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public List<ItemId> searchItems(String q) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Object getBookById(Long bookId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Object getBooksByCategory(String categoryName) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Object searchBooks(String query) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Object getRecommendedBooks() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Object getNewBooks() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public Object getRankingBooks() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	
	
}

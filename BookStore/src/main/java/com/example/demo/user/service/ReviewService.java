package com.example.demo.user.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.user.dto.ReviewDto;
import com.example.demo.user.entity.Comment;
import com.example.demo.user.repository.CommentMapper;

@Service
public class ReviewService {
	@Autowired 
	private CommentMapper commentMapper;

	public List<Comment> getReviewsForItem(Integer itemId) {
		// TODO 自動生成されたメソッド・スタブ
		return commentMapper.selectById(itemId);
	}

	public void saveReview(ReviewDto comment, Integer userId) {
		// TODO 自動生成されたメソッド・スタブ
		commentMapper.saveReview(comment, userId);
	}
	

}

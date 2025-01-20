package com.example.demo.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.user.dto.ReviewDto;
import com.example.demo.user.entity.Comment;
@Mapper
public interface CommentMapper {
	List<Comment> selectAll();

	List<Comment> selectById(Integer itemId);

	void saveReview(ReviewDto comment, Integer userId);
}

package com.example.demo.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.user.entity.Comment;
@Mapper
public interface CommentMapper {
	List<Comment> selectAll();
}

package com.example.demo.admin.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.admin.entity.Admin;

@Mapper
public interface AdminMapper {
	Optional<Admin> findById(int id);
}

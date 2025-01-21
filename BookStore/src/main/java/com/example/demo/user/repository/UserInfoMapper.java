package com.example.demo.user.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.UserInfo;
@Mapper
public interface UserInfoMapper {
	List<UserInfo> selectAll();

	UserInfo getUserInfoById(Integer userId);

	UserInfo userAdd(UserDto userDto);

	UserInfo updateInfo(UserDto userDto);
}

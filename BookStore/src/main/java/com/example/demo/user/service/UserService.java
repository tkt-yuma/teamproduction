package com.example.demo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.UserInfo;
import com.example.demo.user.repository.UserInfoMapper;


@Service
public class UserService {
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	private boolean result = false;
	
	public UserInfo getUserInfoById(Integer userId) {
		// TODO 自動生成されたメソッド・スタブ
		return userInfoMapper.getUserInfoById(userId);
	}

	public boolean addUserInfo(UserDto userDto) {
		// TODO 自動生成されたメソッド・スタブ
		result = userInfoMapper.userAdd(userDto);
		return result;
	}

	public boolean authenticateUser(String userMail, String userPass) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
	
	
}

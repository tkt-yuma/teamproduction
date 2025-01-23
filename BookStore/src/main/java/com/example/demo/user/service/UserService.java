package com.example.demo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.UserInfo;
import com.example.demo.user.entity.UserLogin;
import com.example.demo.user.repository.UserInfoMapper;
import com.example.demo.user.repository.UserLoginMapper;


@Service
public class UserService {
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	private UserLoginMapper userLoginMapper;
	
	private boolean result = false;
	
	public UserInfo getUserInfoById(Integer userId) {
		// TODO 自動生成されたメソッド・スタブ
		return userInfoMapper.getUserInfoById(userId);
	}

	public UserInfo addUserInfo(UserDto userDto) {
		// TODO 自動生成されたメソッド・スタブ
		return userInfoMapper.userAdd(userDto);
		
	}

	public UserInfo updateInfo(UserDto userDto) {
		// TODO 自動生成されたメソッド・スタブ
		return  userInfoMapper.updateInfo(userDto);
	}

	public UserLogin updatePass(Integer integer, UserDto userDto) {
		// TODO 自動生成されたメソッド・スタブ
		return userLoginMapper.updatePass(integer, userDto);
		
	}

	public UserLogin addUserLogin(UserDto userDto) {
		// TODO 自動生成されたメソッド・スタブ
		return userLoginMapper.addUserLogin(userDto);
	}
	public int getUserID() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (authentication != null && authentication.isAuthenticated()) {
             // ユーザー情報を獲得
             Object principal = authentication.getPrincipal();
             if(principal instanceof UserDetails) {
            	 return userLoginMapper.findUserId(authentication.getName());
             }
    	}
    	return 0;
    }
	
}

package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.user.service.UserService;

public class Utils {

	@Autowired
	private UserService userService;

	public static String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			return authentication.getName();
		}
		return null;
	}

//	public static int getUserId() {
//		UserService userService = new UserService();
//		return userService.getUserID();
//		
//		
//	}

}

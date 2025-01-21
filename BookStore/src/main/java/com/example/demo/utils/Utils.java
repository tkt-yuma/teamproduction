package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.security.service.details.UsersDetails;
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

	public static int getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsersDetails usersDetails = (UsersDetails) authentication;
		if (usersDetails != null) {
			return usersDetails.getUserId();
		}
		return 0;
	}

}

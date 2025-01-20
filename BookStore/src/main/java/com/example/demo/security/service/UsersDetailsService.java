package com.example.demo.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.user.entity.UserLogin;
import com.example.demo.user.repository.UserLoginMapper;

@Service("UsersDetailsService")
public class UsersDetailsService implements UserDetailsService{
	
	@Autowired
	UserLoginMapper userLoginMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println(username);
		UserLogin user = userLoginMapper.findByUserMail(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + "は存在しません"));
		
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getUserMail())
				.password(user.getUserPass())
				.roles("User")
				.build();
	}

}

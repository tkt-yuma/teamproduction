package com.example.demo.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.admin.entity.Admin;
import com.example.demo.admin.repository.AdminMapper;

@Service("AdminDetailsService")
public class AdminDetailsService implements UserDetailsService{
	
	@Autowired
	AdminMapper adminIdMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminIdMapper.findByName(username)
				.orElseThrow(() -> new UsernameNotFoundException(username + "番管理者は存在しません"));
		return org.springframework.security.core.userdetails.User
				.withUsername(admin.getLoginName())
				.password(admin.getAdminPass())
				.roles("Admin")
				.build();
	}

}

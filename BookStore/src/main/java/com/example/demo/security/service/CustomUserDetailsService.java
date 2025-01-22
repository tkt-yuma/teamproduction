package com.example.demo.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.admin.entity.Admin;
import com.example.demo.admin.repository.AdminMapper;
import com.example.demo.user.entity.UserLogin;
import com.example.demo.user.repository.UserLoginMapper;
@Service
public class CustomUserDetailsService  implements UserDetailsService{

	
	
	

	    @Autowired
	    private AdminMapper adminMapper;

	    @Autowired
	    private UserLoginMapper userMapper;

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        // admin_idテーブルらか探す
	        Optional<Admin> adminOptional = adminMapper.findByName(username);
	        if (adminOptional.isPresent()) {
	            Admin admin = adminOptional.get();
	            return org.springframework.security.core.userdetails.User.builder()
	                .username(admin.getAdminName())
	                .password(admin.getAdminPass())
	                .roles("Admin") // ユーザーをAdminに設定する
	                .build();
	        }

	        // admin_idテーブルがなかったら、userloginテーブルに探す
	        Optional<UserLogin> userOptional = userMapper.findByUserMail(username);
	        if (userOptional.isPresent()) {
	            UserLogin user = userOptional.get();
	            return org.springframework.security.core.userdetails.User.builder()
	                .username(user.getUserMail())
	                .password(user.getUserPass())
	                .roles("User") // ユーザーをUserに設定する
	                .build();
	        }

	        //　両方のテーブルに見つからなかったら、例外処理する
	        throw new UsernameNotFoundException("ユーザーが見つからなかった： " + username);
	    }

}

package com.example.demo.security.service.details;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.user.entity.UserLogin;

public class UsersDetails implements UserDetails{
	
	private final UserLogin userLogin;
	
	private Collection<GrantedAuthority> authorities;
	
	public UsersDetails(UserLogin userLogin) {
		this.userLogin = userLogin;
		this.authorities = new ArrayList<>();
		this.authorities.add(new SimpleGrantedAuthority("User"));
		}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO 自動生成されたメソッド・スタブ
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO 自動生成されたメソッド・スタブ
		return userLogin.getUserPass();
	}

	@Override
	public String getUsername() {
		// TODO 自動生成されたメソッド・スタブ
		return userLogin.getUserMail();
	}
	
	public int getUserId() {
		return userLogin.getUserId();
	}
	
	public UserLogin getUserLogin() {
		return this.userLogin;
	}

}

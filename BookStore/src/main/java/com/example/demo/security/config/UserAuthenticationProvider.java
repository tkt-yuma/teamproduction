package com.example.demo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.security.service.UsersDetailsService;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

	private final UsersDetailsService usersDetailsService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserAuthenticationProvider(UsersDetailsService usersDetailsService, @Lazy PasswordEncoder passwordEncoder) {
		this.usersDetailsService = usersDetailsService;
		this.passwordEncoder = passwordEncoder;

	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		UserDetails userDetails = usersDetailsService.loadUserByUsername(username);

		if (passwordEncoder.matches(password, userDetails.getPassword())) {
			return new UsernamePasswordAuthenticationToken(
					userDetails,
					password,
					userDetails.getAuthorities());
		} else {
			throw new BadCredentialsException("Invalid username or password");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {

		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}

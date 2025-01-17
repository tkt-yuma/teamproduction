package com.example.demo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.security.service.AdminDetailsService;

@Configuration
public class AdminAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	@Qualifier("AdminDetailsService")

	private final AdminDetailsService adminDetailsService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public AdminAuthenticationProvider(AdminDetailsService adminDetailsService, @Lazy PasswordEncoder passwordEncoder) {
		this.adminDetailsService = adminDetailsService;
		this.passwordEncoder = passwordEncoder;

	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		UserDetails adminDetails = adminDetailsService.loadUserByUsername(username);

		if (passwordEncoder.matches(password, adminDetails.getPassword())) {
			return new UsernamePasswordAuthenticationToken(
					adminDetails,
					password,
					adminDetails.getAuthorities());
		} else {
			throw new BadCredentialsException("Invalid username or password");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {

		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}

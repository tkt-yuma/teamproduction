package com.example.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	@Order(1)
	public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf
				.ignoringRequestMatchers("/admin/adminLogout"))
				.formLogin(form -> form
						.loginPage("/admin/adminlogin/admin-login")
						.loginProcessingUrl("/admin/authenticate")
						.defaultSuccessUrl("/admin/adminprivate/admin-top")
						.failureUrl("/admin/login?failure")
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/admin/adminLogout")
						.logoutSuccessUrl("/admin/adminlogin/admin-login")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID"))
				.securityMatcher("/admin/**")
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/admin/adminlogin/**").permitAll()
						.anyRequest().hasRole("Admin"));
		return http.build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain customerFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf
				.ignoringRequestMatchers("/user/userLogout"))
				.formLogin(form -> form
						.loginPage("/user/userpublic/user-login")
						.loginProcessingUrl("/user/authenticate")
						.defaultSuccessUrl("/user/userpublic/product")
						.failureUrl("/user/login?failure")
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/user/userLogout")
						.logoutSuccessUrl("/user/userpublic/user-login")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID"))
				.securityMatcher("/user/**")
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/user/userpublic/**").permitAll()
						.anyRequest().hasRole("User"));

		return http.build();
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

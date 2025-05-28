package com.employeemanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	 @Bean
	 public SecurityFilterChain securityFilterChain(HttpSecurity http)
	 throws Exception {
	
	 http.authorizeHttpRequests(auth -> auth
	 .requestMatchers("/login", "/", "/style.css", "/js.js", "/images/**").permitAll()
	 .requestMatchers("/admin").hasRole("ADMIN")
	 .requestMatchers("/user").hasRole("USER").anyRequest()
	 .authenticated())
	 .formLogin(form -> form.loginPage("/login")
	 .successHandler(customSuccessHandler()).permitAll())
	 .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll());
	
	 return http.build();
	 }

//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//		// disable CSRF for testing purpose
//		// csrf(csrf -> csrf.disable())
//		// disable login form
//		// disable basic auth
//		http.csrf(csrf -> csrf.disable())
//				.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
//				.logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll())
//				.formLogin(login -> login.disable())
//				.httpBasic(httpBasic -> httpBasic.disable());
//
//		return http.build();
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
		return NoOpPasswordEncoder.getInstance(); // ⚠️ Only for dev/testing!
	}

	@Bean
	public AuthenticationSuccessHandler customSuccessHandler() {

		return (request, response, authentication) -> {
			String username = authentication.getName();
			System.out.println("Logged in as: " + username);

			if ("admin".equalsIgnoreCase(username)) {
				response.sendRedirect("/home");
			} else if ("user".equalsIgnoreCase(username)) {
				response.sendRedirect("/home");
			} else {
				response.sendRedirect("/");
			}

		};

	}

}

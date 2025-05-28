package com.employeemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.employeemanagement.model.User;
import com.employeemanagement.repo.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepo;

	public MyUserDetailsService() {
		System.out.println("MyUserDetailsService has been instantiated!");
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		User user = userRepo.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found!"));

		System.out.println(this.getClass().getName() + " " + user.getUsername()
				+ " " + user.getPassword());
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getUsername())
				.password(user.getPassword())
				.roles(user.getRole().toUpperCase())
				.build();

	}

}

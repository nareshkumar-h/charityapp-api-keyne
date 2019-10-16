package com.revature.charityspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.revature.charityspring.service.AdminService;
import com.revature.services.DonationService;
import com.revature.services.UserService;

@Configuration
public class BeanConfig {
	@Bean
	public AdminService adminService()
	{
		return new AdminService();
	}
	@Bean
	public DonationService donationService()
	{
		return new DonationService();
	}
	@Bean
	public UserService userService()
	{
		return new UserService();
	}
	
	

}

package com.revature.charityspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.revature.services.AdminService;
import com.revature.services.DonationService;

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

}

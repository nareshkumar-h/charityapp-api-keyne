package com.revature.charityspring.repository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.charityspring.model.Admin;
import com.revature.charityspring.repository.AdminRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminRepositoryTest {
	
	@Autowired
	AdminRepository admin;

	@Test
	public void loginTest() {
		String email="k@gmail.com";
		String password="Keyne@123";
		
		Admin adminDetails=admin.findByEmailAndPassword(email, password);
		assertNotNull(adminDetails);
	}

}

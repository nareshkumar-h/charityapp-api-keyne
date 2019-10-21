package com.revature.charityspring.repository;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import com.revature.charityspring.model.Donor;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DonorRepositoryTest {
	
	@Autowired
	DonorRepository donorRespository;

	@Test
	public void loginTest() {
		String email="k@gmail.com";
		String password="Keyne@123";
		
		Donor donorDetails=donorRespository.findByEmailAndPassword(email, password);
		assertNotNull(donorDetails);
	}

	}



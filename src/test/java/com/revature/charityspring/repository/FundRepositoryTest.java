package com.revature.charityspring.repository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;




@RunWith(SpringRunner.class)
@SpringBootTest
public class FundRepositoryTest {
	
	@Autowired
	FundRepository fund;

	@Test
	public void fundRequestTest() {
		
		Integer requestType=1;
		double amount=10000.0;
		
		int donor=fund.updateDonationByAdmin(amount, requestType);
		assertNotNull(donor);
		
		
		
		
	}

}

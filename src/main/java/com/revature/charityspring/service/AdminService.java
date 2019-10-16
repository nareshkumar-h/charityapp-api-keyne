package com.revature.charityspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.charityspring.exception.ServiceException;
import com.revature.charityspring.model.Admin;
import com.revature.charityspring.repository.AdminRepository;
import com.revature.util.MessageConstant;


@Service
public class AdminService {
	@Autowired
	AdminRepository adminRepositoryObj;

	public Admin adminLogin(Admin adminObj) throws ServiceException {
		Admin admin = null;
		String email = adminObj.getEmail();
		String password = adminObj.getPassword();
		admin = adminRepositoryObj.findByEmailAndPassword(email, password);
		if (admin == null) {
			throw new ServiceException(MessageConstant.INVALID_LOGIN_CREDENTIALS);
		}
		return admin;

	}
	
}


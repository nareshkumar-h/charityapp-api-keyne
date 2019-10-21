package com.revature.charityspring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.charityspring.dto.AdminDto;
import com.revature.charityspring.exception.DBException;
import com.revature.charityspring.exception.ServiceException;
import com.revature.charityspring.model.Admin;
import com.revature.charityspring.model.AdminActivity;
import com.revature.charityspring.repository.AdminActivityRepository;
import com.revature.charityspring.repository.AdminRepository;



@Service
public class AdminService {
	@Autowired
	AdminRepository adminRepositoryObj;
	
	
	@Autowired
	AdminActivityRepository adminActivityRepositoryObj;
	
	
	public AdminDto adminLogin(Admin adminObj) throws ServiceException {
		Admin admin = null;
		String email = adminObj.getEmail();
		String password = adminObj.getPassword();
		admin = adminRepositoryObj.findByEmailAndPassword(email, password);
		if (admin == null) {
			throw new ServiceException("unable to display");
		}
		AdminDto adminDto=new AdminDto();
		adminDto.setId(admin.getId());
		adminDto.setEmail(admin.getEmail());
		adminDto.setName(admin.getName());
		return adminDto;

	}


	
	public List<AdminActivity> listAdminTransaction() throws DBException {
		List<AdminActivity> list = null;
		list = adminActivityRepositoryObj.findAll();
		return list;
	}
	
}


package com.revature.charityspring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.charityspring.dto.DonorDto;
import com.revature.charityspring.exception.DBException;
import com.revature.charityspring.exception.ServiceException;
import com.revature.charityspring.model.Donor;
import com.revature.charityspring.repository.DonorRepository;

@Service
public class DonorService {
	@Autowired
	DonorRepository donorRepositoryObj;

	public DonorDto donorLogin(Donor donorObj) throws ServiceException {
		Donor donor = null;
		String email = donorObj.getEmail();
		String password = donorObj.getPassword();
		donor = donorRepositoryObj.findByEmailAndPassword(email, password);
		if (donor == null) {
			throw new ServiceException("Invalid login");
		}
		DonorDto donorDto = new DonorDto();
		donorDto.setEmail(donor.getEmail());
		donorDto.setId(donor.getId());
		donorDto.setName(donor.getName());
		return donorDto;

	}

	@Transactional
	public void donorRegister(Donor donor) throws ServiceException {
		donorRepositoryObj.save(donor);

		if (donor == null) {
			throw new ServiceException("invalid login");
		}
	}

	public List<Donor> findAll() throws DBException {
		List<Donor> list = null;
		list = donorRepositoryObj.findAll();
		return list;
	}
}

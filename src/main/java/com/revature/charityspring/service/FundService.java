package com.revature.charityspring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.charityspring.dao.FundDAO;
import com.revature.charityspring.exception.ServiceException;
import com.revature.charityspring.model.DonationRequest;
import com.revature.charityspring.repository.FundRepository;
import com.revature.exception.DBException;
import com.revature.model.DonorActivity;
import com.revature.util.MessageConstant;

@Service
public class FundService {
	@Autowired
	FundRepository fundRepositoryObj;

	@Autowired
	FundDAO fundDAO;

	public List<DonationRequest> findAll() throws DBException {
		List<DonationRequest> list = null;
		list = fundRepositoryObj.findAll();
		return list;

	}

	// @Transactional
	public void addDonation(DonationRequest donation) throws ServiceException {
		try {
			DonationRequest dr = fundRepositoryObj.findByRequestType(donation.getRequestType());
			System.out.println(dr);
			if (dr == null) {
				fundRepositoryObj.save(donation);
			} else {
				throw new ServiceException("Request Type exists");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
			
		}
	}

	@Transactional
	public void updateDonationsByAdmin(DonationRequest donation) throws DBException {

		double requestAmount = donation.getRequestAmount();
		int id = donation.getId();

		fundRepositoryObj.updateDonationByAdmin(requestAmount, id);
	}

	@Transactional
	public List<DonorActivity> findAllDonor() throws DBException {
		List<DonorActivity> list = null;
		try {
			list = fundDAO.findAll();
		} catch (DBException e) {

			throw new DBException(e.getMessage());
		}
		return list;
	}

}

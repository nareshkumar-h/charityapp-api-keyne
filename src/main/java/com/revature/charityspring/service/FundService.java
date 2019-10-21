package com.revature.charityspring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.charityspring.dao.FundDAO;

import com.revature.charityspring.exception.ServiceException;
import com.revature.charityspring.model.AdminActivity;
import com.revature.charityspring.model.DonationRequest;
import com.revature.charityspring.model.DonorActivity;
import com.revature.charityspring.repository.AdminActivityRepository;
import com.revature.charityspring.repository.ContributeFundRepository;
import com.revature.charityspring.repository.FundRepository;
import com.revature.charityspring.exception.DBException;

@Service
public class FundService {
	@Autowired
	FundRepository fundRepositoryObj;

	@Autowired
	FundDAO fundDAO;

	@Autowired
	AdminActivityRepository adminActivity;
	
	@Autowired
	ContributeFundRepository contribute;

	public List<DonationRequest> findAll() throws DBException {
		List<DonationRequest> list = null;
		list = fundDAO.findAllRequest();
		return list;

	}

	public List<DonationRequest> findInitialRequest() throws DBException {
		List<DonationRequest> list = null;
		list = fundRepositoryObj.findAll();
		return list;
	}

	public void addDonation(DonationRequest donation, Integer adminId) throws ServiceException {
		try {
			DonationRequest dr = fundRepositoryObj.findByRequestType(donation.getRequestType());
			System.out.println(dr);
			if (dr == null) {
				DonationRequest save = fundRepositoryObj.save(donation);
				
				
				AdminActivity obj = new AdminActivity();
				obj.setAdminId(adminId);
				obj.setRequestType(donation.getRequestType());
				obj.setRequestAmount(donation.getRequestAmount());
				adminActivity.save(obj);
			} else {
				throw new ServiceException("Request Type exists");

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
			
		}
	}
	public void updateDonation(DonationRequest donation,Integer adminId) throws ServiceException {

		try {
			DonationRequest dr = fundRepositoryObj.findById(donation.getId()).get();

			if (dr == null) {
				throw new ServiceException("Request Type Not exists");
			} else {
				Double requestAmount = donation.getRequestAmount();
				String requestType = donation.getRequestType();
				System.out.println(requestType + "-" + requestAmount);
				fundRepositoryObj.updateDonationByAdmin(requestAmount, donation.getId());
				

				AdminActivity obj = new AdminActivity();
				obj.setAdminId(adminId);
				obj.setRequestType(dr.getRequestType());
				obj.setRequestAmount(donation.getRequestAmount());
				adminActivity.save(obj);
			
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());

		}
	}

	/*
	 * @Transactional public void updateDonationsByAdmin(DonationRequest donation)
	 * throws DBException {
	 * 
	 * double requestAmount = donation.getRequestAmount(); int id =
	 * donation.getId();
	 * 
	 * fundRepositoryObj.updateDonationByAdmin(requestAmount, id);
	 * 
	 * }
	 */

	@Transactional
	public List<DonorActivity> findAllDonor() throws DBException {
		List<DonorActivity> list = null;
		list = contribute.findAll();
		return list;
	}

}

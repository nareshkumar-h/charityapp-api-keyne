package com.revature.charityspring.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.charityspring.exception.ServiceException;

import com.revature.charityspring.model.DonorActivity;
import com.revature.charityspring.repository.ContributeFundRepository;
import com.revature.charityspring.repository.FundRepository;
import com.revature.charityspring.util.MessageConstant;


@Service
public class ContributeFundService {
	@Autowired
	ContributeFundRepository contribute;
	@Autowired
	FundRepository fund;
	@Transactional
    public void donorFund(DonorActivity donor) throws ServiceException {
		contribute.save(donor);
		
        if (donor == null) {
            throw new ServiceException(MessageConstant.INVALID_LOGIN_CREDENTIALS);
        }
    }
	

}

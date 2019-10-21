package com.revature.charityspring.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.charityspring.model.DonationRequest;


public interface FundRepository extends JpaRepository<DonationRequest, Integer>{
	@Modifying
    @Transactional
    @Query("UPDATE DonationRequest f SET"
            
    	
            + " f.requestAmount = f.requestAmount +:requestAmount WHERE f.id = :requestType")
           
    public int updateDonationByAdmin(
           
    
            @Param("requestAmount") Double amount,
            
            @Param("requestType") Integer requestType
           
            );
	
	 @Query("SELECT a FROM DonationRequest a WHERE a.requestType = :requestType")
	public DonationRequest findByRequestType(
			 @Param("requestType")	String requestType);

	

}

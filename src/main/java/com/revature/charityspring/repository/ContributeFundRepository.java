package com.revature.charityspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.charityspring.model.DonorActivity;

public interface ContributeFundRepository extends JpaRepository<DonorActivity, Integer>{
	

}

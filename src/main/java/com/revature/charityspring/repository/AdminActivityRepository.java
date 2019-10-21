package com.revature.charityspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.charityspring.model.AdminActivity;

@Repository
public interface AdminActivityRepository extends JpaRepository<AdminActivity, Integer>{
	

}

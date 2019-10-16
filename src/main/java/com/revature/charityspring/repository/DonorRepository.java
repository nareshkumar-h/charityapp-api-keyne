package com.revature.charityspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.charityspring.model.Donor;

public interface DonorRepository extends JpaRepository<Donor, Integer>{
	@Query("SELECT a FROM Donor a WHERE a.email = :email AND a.password = :password")
    Donor findByEmailAndPassword(
            @Param("email") String email,
            @Param("password") String password
            );
	

}

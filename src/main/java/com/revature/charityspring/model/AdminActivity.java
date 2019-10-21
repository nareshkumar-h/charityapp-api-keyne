package com.revature.charityspring.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "admin_transaction")
public class AdminActivity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "admin_id ")

	private int adminId;

	@Column(name = "request_type ")
	private String requestType;

	@Column(name = "request_amount  ")
	private double requestAmount;
	
	@Column(name = "date")
	private LocalDate date=LocalDate.now();


}

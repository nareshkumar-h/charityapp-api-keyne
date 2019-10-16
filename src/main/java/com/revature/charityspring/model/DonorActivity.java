package com.revature.charityspring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "activity")

public class DonorActivity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "user_id ")

	private int userId;

	@Column(name = "amount_funded ")
	private double amountFunded;

	@Column(name = "request_id  ")
	private int requestId;

}

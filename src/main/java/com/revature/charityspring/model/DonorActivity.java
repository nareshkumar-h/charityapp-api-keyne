package com.revature.charityspring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private Donor donor;

	@Column(name = "amount_funded ")
	private double amountFunded;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="request_id")
    private DonationRequest request; 

}

package com.revature.charityspring.dto;

import lombok.Data;

@Data
public class FundDto {
	private String name;
	private String email;
	private String requestType;
	private double requestAmount;
	private double fundedAmount;

}

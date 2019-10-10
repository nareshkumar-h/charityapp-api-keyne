package com.revature.charityspring.controller.admincontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.revature.model.DonationRequest;
import com.revature.services.DonationService;

@RestController
@RequestMapping("admin")
public class RaiseFundRequestController {
	@Autowired
	DonationService donationService;
	
	 @GetMapping("/raiseFundRequest")
	public String addRequest(@RequestParam("requestType")String requestType, @RequestParam("requestAmount") double requestAmount) {

		String errorMessage = null;
		String message = null;
		DonationRequest dr = null;
		
		try {
			dr = new DonationRequest();

			dr.setRequestType(requestType);
			dr.setRequestAmount(requestAmount);
			donationService.addDonations(dr);
			message = "Success";

		} catch (Exception e) {
		
			errorMessage = e.getMessage();
		}

		// Prepare JSON Object

		JsonObject obj = new JsonObject();
		if (message != null) {

			obj.addProperty("message", message);
		} else if (errorMessage != null) {
			obj.addProperty("errorMessage", errorMessage);
		}

		return obj.toString();

	}
	


}

package com.revature.charityspring.controller.admincontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revature.exception.DBException;
import com.revature.model.DonationRequest;
import com.revature.services.DonationService;

@RestController
@RequestMapping("admin")
public class ListFundRequestController {
	@Autowired
	DonationService donationService;
	
	
	 @GetMapping("/listFundRequest")
	public  String listRequest() {
		String json = null;
		List<DonationRequest> list = null;
		String errorMessage = null;
		try {
			
			list=donationService.findAll();
			

		} catch (DBException e) {
			errorMessage = e.getMessage();

		}
		if (list != null) {
			Gson gson = new Gson();
			json = gson.toJson(list);
		}
		if (errorMessage != null) {
			JsonObject obj = new JsonObject();
			obj.addProperty("errorMessage", errorMessage);
		}
		System.out.println("List"+json);
		return json;

	}

}

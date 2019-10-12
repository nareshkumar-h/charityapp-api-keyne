package com.revature.charityspring.controller.admincontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revature.exception.DBException;
import com.revature.model.Admin;
import com.revature.model.DonationRequest;
import com.revature.model.DonorActivity;
import com.revature.services.AdminService;
import com.revature.services.DonationService;
import com.revature.services.UserService;

@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@Autowired
	DonationService donationService;

	@Autowired
	UserService userService;

	@PostMapping("/login")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password) {

		String errorMessage = null;

		Admin user = null;
		try {
			user = adminService.adminLogin(email, password);

			if (user == null) {
				throw new DBException("Invalid Email/Password");
			}

		} catch (Exception e) {
			errorMessage = e.getMessage();
		}

		// Prepare JSON Object
		String json = null;
		Gson gson = new Gson();
		if (user != null) {
			json = gson.toJson(user);
		} else if (user == null) {
			JsonObject obj = new JsonObject();
			obj.addProperty("errorMessage", errorMessage);
			json = obj.toString();
		}

		return json;

	}

	@PutMapping("/updateFundRequest")
	public String updateRequest(@RequestParam("requestType") String requestType,
			@RequestParam("requestAmount") double requestAmount) {

		String errorMessage = null;
		String message = null;
		DonationRequest dr = null;
		try {
			dr = new DonationRequest();

			dr.setRequestType(requestType);

			dr.setRequestAmount(requestAmount);

			donationService.updateDonationsByAdmin(dr);
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

	@GetMapping("/listDonorContribution")
	public String listDonor() {
		String json = null;
		List<DonorActivity> list = null;
		String errorMessage = null;
		try {

			list = userService.findAll();

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
			json = errorMessage;
		}
		System.out.println("List" + json);

		return json;

	}

	@GetMapping("/raiseFundRequest")
	public String addRequest(@RequestParam("requestType") String requestType,
			@RequestParam("requestAmount") double requestAmount) {

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

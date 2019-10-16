package com.revature.charityspring.controller.admincontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import com.revature.charityspring.dto.Message;
import com.revature.charityspring.model.Admin;
import com.revature.charityspring.model.DonationRequest;
import com.revature.charityspring.model.Donor;
import com.revature.charityspring.service.AdminService;
import com.revature.charityspring.service.DonorService;
import com.revature.charityspring.service.FundService;
import com.revature.exception.DBException;

import com.revature.model.DonorActivity;

import com.revature.services.DonationService;
import com.revature.services.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@Autowired
	DonationService donationService;

	@Autowired
	FundService fundService;

	@Autowired
	DonorService donorService;

	@Autowired
	UserService userService;

	@PostMapping("/login")
	@ApiOperation(value = "Adminlogin API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Admin.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })

	public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam("password") String password) {
		String errorMessage = null;

		Admin adminobj = null;
		try {
			Admin admin = new Admin();
			admin.setEmail(email);
			admin.setPassword(password);
			adminobj = adminService.adminLogin(admin);

		} catch (Exception e) {
			errorMessage = e.getMessage();
		}

		Message message = null;
		if (adminobj != null) {
			return new ResponseEntity<>(adminobj, HttpStatus.OK);
		} else {
			message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/updateFundRequest")
	@ApiOperation(value = "UpdateFundRequest API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = DonationRequest.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	public ResponseEntity<?> updateRequest(@RequestParam("requestAmount") double requestAmount,
			@RequestParam("id") int id) {

		String errorMessage = null;

		DonationRequest donationRequest = null;
		try {
			donationRequest = new DonationRequest();

			donationRequest.setId(id);

			// donationRequest.setRequestType(requestType);

			donationRequest.setRequestAmount(requestAmount);

			fundService.updateDonationsByAdmin(donationRequest);

		} catch (Exception e) {

			errorMessage = e.getMessage();

		}

		Message message = null;
		if (donationRequest != null) {
			return new ResponseEntity<>(donationRequest, HttpStatus.OK);
		} else {
			message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/listDonorContribution")
	@ApiOperation(value = "ListDonorContribution API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = DonorActivity.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })

	public ResponseEntity<?> listDonor() {

		List<DonorActivity> list = null;
		String errorMessage = null;
		try {

			list = fundService.findAllDonor();

		} catch (DBException e) {
			errorMessage = e.getMessage();

		}
		Message message = null;
		if (list != null) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/listRegisteredDonor")
	@ApiOperation(value = "ListDonor API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Donor.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })

	public ResponseEntity<?> listRegisteredDonor() {

		List<Donor> list = null;
		String errorMessage = null;
		try {

			list = donorService.findAll();

		} catch (DBException e) {
			errorMessage = e.getMessage();

		}
		Message message = null;
		if (list != null) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/raiseFundRequest")
	@ApiOperation(value = "RaiseFundRequest API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = DonationRequest.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	public ResponseEntity<?> addRequest(@RequestParam("requestType") String requestType,
			@RequestParam("requestAmount") double requestAmount) {

		String errorMessage = null;
		DonationRequest donationRequest = null;

		try {
			donationRequest = new DonationRequest();

			donationRequest.setRequestType(requestType);
			donationRequest.setRequestAmount(requestAmount);
			fundService.addDonation(donationRequest);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {

			errorMessage = e.getMessage();

			Message message = null;

			message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/listFundRequest")
	@ApiOperation(value = "listFundRequest API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = DonationRequest.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })

	public ResponseEntity<?> listFundRequest() {

		List<DonationRequest> list = null;
		String errorMessage = null;
		try {

			list = fundService.findAll();

		} catch (DBException e) {
			errorMessage = e.getMessage();

		}
		Message message = null;
		if (list != null) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}

	}

}

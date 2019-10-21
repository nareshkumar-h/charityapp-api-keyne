package com.revature.charityspring.controller.admincontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import com.revature.charityspring.dto.AdminDto;
import com.revature.charityspring.dto.FundDto;
import com.revature.charityspring.dto.Message;
import com.revature.charityspring.exception.DBException;
import com.revature.charityspring.model.Admin;
import com.revature.charityspring.model.AdminActivity;
import com.revature.charityspring.model.DonationRequest;
import com.revature.charityspring.model.Donor;
import com.revature.charityspring.model.DonorActivity;
import com.revature.charityspring.service.AdminService;
import com.revature.charityspring.service.DonorService;
import com.revature.charityspring.service.FundService;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@Autowired
	FundService fundService;

	@Autowired
	DonorService donorService;

	@PostMapping("/login")
	@ApiOperation(value = "Adminlogin API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Admin.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	
	public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam("password") String password) {
		String errorMessage = null;

		AdminDto adminobj = null;
		try {
			AdminDto adminDto=new AdminDto();
			Admin admin = new Admin();
			adminDto.setEmail(email);
			adminDto.setPassword(password);

			admin.setEmail(adminDto.getEmail());
			admin.setPassword(adminDto.getPassword());
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

	
	
	@GetMapping("/listDonorContribution")
	@ApiOperation(value = "ListDonorContribution API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = DonorActivity.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })

	public ResponseEntity<?> listDonor() {

		List<DonorActivity> list = null;
		String errorMessage = null;
		try {

			list = fundService.findAllDonor();
			return new ResponseEntity<>(list, HttpStatus.OK);

		} catch (DBException e) {
			errorMessage = e.getMessage();

			Message message = null;

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
			return new ResponseEntity<>(list, HttpStatus.OK);

		} catch (DBException e) {
			errorMessage = e.getMessage();

			Message message = null;

			message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/raiseFundRequest/{id}")
	@ApiOperation(value = "RaiseFundRequest API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = DonationRequest.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	public ResponseEntity<?> addRequest(@PathVariable("id") Integer requestTypeId,
			@RequestParam("requestAmount") double requestAmount,@RequestParam("adminId") Integer adminId) {

		String errorMessage = null;
		DonationRequest donationRequest = null;

		try {
			donationRequest = new DonationRequest();
			donationRequest.setId(requestTypeId);
			
			donationRequest.setRequestAmount(requestAmount);
			

			fundService.updateDonation(donationRequest,adminId);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {

			errorMessage = e.getMessage();

			Message message = null;

			message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/raiseFundRequest")
	@ApiOperation(value = "RaiseFundRequest API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = DonationRequest.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	public ResponseEntity<?> addRequest(@RequestParam("requestType") String requestType, @RequestParam("adminId") Integer adminId,
			@RequestParam("requestAmount") double requestAmount) {

		String errorMessage = null;
		DonationRequest donationRequest = null;

		try {
			donationRequest = new DonationRequest();
			
			donationRequest.setRequestType(requestType);
			donationRequest.setRequestAmount(requestAmount);
			

			fundService.addDonation(donationRequest,adminId);
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

			list = fundService.findInitialRequest();

			return new ResponseEntity<>(list, HttpStatus.OK);

		} catch (DBException e) {
			errorMessage = e.getMessage();

			Message message = null;

			message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("/listAdminTransaction")
	@ApiOperation(value = "listAdminTransaction API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = AdminActivity.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })

	public ResponseEntity<?> listAdminTransaction() {

		List<AdminActivity> list = null;
		String errorMessage = null;
		try {

			list = adminService.listAdminTransaction();

			return new ResponseEntity<>(list, HttpStatus.OK);

		} catch (DBException e) {
			errorMessage = e.getMessage();

			Message message = null;

			message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}

	}

}

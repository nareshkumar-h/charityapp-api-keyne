package com.revature.charityspring.controller.donorcontroller;

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
import com.revature.charityspring.exception.ServiceException;
import com.revature.charityspring.model.DonationRequest;
import com.revature.charityspring.model.Donor;
import com.revature.charityspring.model.DonorActivity;
import com.revature.charityspring.service.ContributeFundService;
import com.revature.charityspring.service.DonorService;
import com.revature.charityspring.service.FundService;
import com.revature.exception.DBException;


import com.revature.model.User;
import com.revature.services.DonationService;
import com.revature.services.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("donor")
public class DonorController {
	
	@Autowired
	DonorService donorService;

	@Autowired
	UserService userService;

	@Autowired
	DonationService donationService;
	
	@Autowired
	FundService fundService;
	
	@Autowired
	ContributeFundService contribute;


	@PostMapping("/login")
	@ApiOperation(value = "Donorlogin API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = User.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam("password") String password) {

		String errorMessage = null;

		Donor userobj = null;
		try {
			Donor user=new Donor();
			user.setEmail(email);
			user.setPassword(password);
			userobj = donorService.donorLogin(user);

		} catch (Exception e) {

			errorMessage = e.getMessage();
		}

		Message message = null;
		if (userobj != null) {
			return new ResponseEntity<>(userobj, HttpStatus.OK);
		} else {
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

	
	@PostMapping("/donorRegistration")
	@ApiOperation(value = "Donor Registration API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = User.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	public ResponseEntity<?> register(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("password") String password) {

		String errorMessage = null;

		Donor user = null;
		try {
			user = new Donor();

			user.setName(name);
			user.setEmail(email);
			user.setPassword(password);

			donorService.donorRegister(user);

		} catch (ServiceException e) {
			errorMessage = e.getMessage();
			
		}
		Message message = null;
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}

	}
	@GetMapping("/ContributeToFundRequest")
	@ApiOperation(value = "Contribute To FundRequest API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = DonorActivity.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	public ResponseEntity<?> contributeRequest(@RequestParam("requestAmount") double amountFunded, @RequestParam("requestId") int requestId,
			@RequestParam("userId") int userId) {
		
		String errorMessage = null;
		DonorActivity donation=null;
		
		donation = new DonorActivity();
		donation.setAmountFunded(amountFunded);
		donation.setRequestId(requestId);
		donation.setUserId(userId);
		try {
			contribute.donorFund(donation);
		} catch (ServiceException e) {
			
		}
	
		Message message = null;
		if (donation != null) {
			return new ResponseEntity<>(donation, HttpStatus.OK);
		} else {
			message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}

		

}

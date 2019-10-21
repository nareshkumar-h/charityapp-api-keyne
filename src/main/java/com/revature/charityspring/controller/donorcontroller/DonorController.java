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

import com.revature.charityspring.dto.DonorDto;
import com.revature.charityspring.dto.Message;
import com.revature.charityspring.exception.DBException;
import com.revature.charityspring.exception.ServiceException;
import com.revature.charityspring.model.DonationRequest;
import com.revature.charityspring.model.Donor;
import com.revature.charityspring.model.DonorActivity;
import com.revature.charityspring.service.ContributeFundService;
import com.revature.charityspring.service.DonorService;
import com.revature.charityspring.service.FundService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("donor")
public class DonorController {

	@Autowired
	DonorService donorService;

	@Autowired
	FundService fundService;

	@Autowired
	ContributeFundService contribute;

	@PostMapping("/login")
	@ApiOperation(value = "Donorlogin API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Donor.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	
	public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam("password") String password) {

		String errorMessage = null;

		DonorDto userobj = null;
		try {
			DonorDto donotDto=new DonorDto();
			Donor user=new Donor();
			
			donotDto.setEmail(email);
			donotDto.setPassword(password);
			

			user.setEmail(donotDto.getEmail());
			user.setPassword(donotDto.getPassword());
			
			
			userobj = donorService.donorLogin(user);
			return new ResponseEntity<>(userobj, HttpStatus.OK);

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
			return new ResponseEntity<>(list, HttpStatus.OK);

		} catch (DBException e) {
			errorMessage = e.getMessage();

			Message message = null;

			message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/donorRegistration")
	@ApiOperation(value = "Donor Registration API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Donor.class),
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
			return new ResponseEntity<>(user, HttpStatus.OK);

		} catch (ServiceException e) {
			errorMessage = e.getMessage();

			Message message = null;

			message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/ContributeToFundRequest")
	@ApiOperation(value = "Contribute To FundRequest API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = DonorActivity.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	public ResponseEntity<?> contributeRequest(@RequestParam("requestAmount") double amountFunded,
			@RequestParam("requestId") int requestId, @RequestParam("userId") int userId) {

		String errorMessage = null;
		DonorActivity donation = null;
		

		donation = new DonorActivity();
		donation.setAmountFunded(amountFunded);
		DonationRequest requestObj = new DonationRequest();
		requestObj.setId(requestId);
		donation.setRequest(requestObj);
		Donor donorObj = new Donor();
		donorObj.setId(userId);
		donation.setDonor(donorObj);
		try {
			contribute.donorFund(donation);
			return new ResponseEntity<>(donation, HttpStatus.OK);
		} catch (ServiceException e) {

			Message message = null;

			message = new Message(errorMessage);
			return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
		}
	}

}

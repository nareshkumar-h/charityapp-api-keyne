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
import com.revature.exception.DBException;
import com.revature.model.DonationRequest;
import com.revature.model.DonorActivity;
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
	UserService userService;

	@Autowired
	DonationService donationService;

	@PostMapping("/login")
	@ApiOperation(value = "Donorlogin API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = User.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })
	public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam("password") String password) {

		String errorMessage = null;

		User user = null;
		try {
			user = userService.donorLogin(email, password);

		} catch (Exception e) {

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

	@GetMapping("/listFundRequest")
	@ApiOperation(value = "listFundRequest API")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = DonorActivity.class),
			@ApiResponse(code = 400, message = "Invalid Credentials", response = Message.class) })

	public ResponseEntity<?> listFundRequest() {

		List<DonationRequest> list = null;
		String errorMessage = null;
		try {

			list = donationService.findAll();

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

		User user = null;
		try {
			user = new User();

			user.setName(name);
			user.setEmail(email);
			user.setPassword(password);

			userService.registerDonor(user);

		} catch (DBException e) {

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

}

package com.revature.charityspring.controller.donorcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revature.exception.DBException;
import com.revature.model.User;
import com.revature.services.UserService;

@RestController
@RequestMapping("donor")
public class DonorLoginController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/login")
	public String login(@RequestParam("email")String email, @RequestParam("password")String password) {

		String errorMessage = null;

		User user = null;
		try {
			user = userService.donorLogin(email, password);
			if (user == null) {
				throw new DBException("Invalid Email/Password ");
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

}

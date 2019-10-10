package com.revature.charityspring.controller.admincontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revature.exception.DBException;
import com.revature.model.Admin;
import com.revature.services.AdminService;

@RestController
@RequestMapping("admin")
public class AdminLoginController {
	
@Autowired
 AdminService adminService;

	
    @PostMapping("/login")
	public String login(@RequestParam("email")String email, @RequestParam("password")String password) {

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
}

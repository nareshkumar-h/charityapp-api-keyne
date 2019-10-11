package com.revature.charityspring.controller.admincontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.revature.exception.DBException;
import com.revature.model.DonorActivity;
import com.revature.services.UserService;


@RestController
@RequestMapping("admin")
public class ListDonorContributionController {
	
	@Autowired
	UserService userService; 
	
	 @GetMapping("/listDonorContribution")
	public  String listDonor() {
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
		System.out.println("List"+json);

		return json;

	}

}

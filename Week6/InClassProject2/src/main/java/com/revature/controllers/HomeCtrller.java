package com.revature.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeCtrller {
	
	@RequestMapping(value="/Home")
	public String home(){
		System.out.println("homeCtrl called");
		return "Home";
	}

}

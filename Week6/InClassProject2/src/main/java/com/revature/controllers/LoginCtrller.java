package com.revature.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.revature.model.Customer;
import com.revature.service.CustService;

@Controller
public class LoginCtrller {

	@Autowired
	private CustService service;

	@RequestMapping("/login")
	public String loginGET() {

		return "/static/features/login.html";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPost(HttpServletRequest req, Customer customer) {
		
		return "login";
	}

	@RequestMapping("/")
	public String welcome() {

		return "/static/features/index.html";
	}
}

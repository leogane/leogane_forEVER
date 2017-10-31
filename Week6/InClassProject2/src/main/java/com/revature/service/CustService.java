package com.revature.service;

import com.revature.model.Customer;

import org.springframework.stereotype.Component;

@Component
public class CustService {
	
	public Customer test(Customer customer) {
		
		Customer authCustomer = null;
		
		if ("monday".equals(customer.getUsername()) && "tuesday".equals(customer.getPassword())) {
			authCustomer = customer;
		} else {
			authCustomer = null;
		}
		
		return authCustomer;
	}

}

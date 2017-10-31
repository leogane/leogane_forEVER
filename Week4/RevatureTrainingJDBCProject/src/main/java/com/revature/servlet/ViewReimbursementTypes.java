package com.revature.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.pojo.ReimbursementType;
import com.revature.service.Service;

@WebServlet("/viewReimbursementTypes")
public class ViewReimbursementTypes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewReimbursementTypes() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<ReimbursementType> reimbursementTypeList = new Service().getReimbursementTypeList();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(reimbursementTypeList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.write(json);
	}
}

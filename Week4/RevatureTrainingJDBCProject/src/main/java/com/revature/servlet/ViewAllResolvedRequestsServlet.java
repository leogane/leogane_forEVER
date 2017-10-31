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
import com.revature.dto.ReimbursementDTO;
import com.revature.service.Service;

@WebServlet("/viewAllResolvedRequests")
public class ViewAllResolvedRequestsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewAllResolvedRequestsServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Service service = new Service();

		List<ReimbursementDTO> resolvedReimbursements = service.getAllResolvedReimbursements();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(resolvedReimbursements);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.write(json);

	}
}

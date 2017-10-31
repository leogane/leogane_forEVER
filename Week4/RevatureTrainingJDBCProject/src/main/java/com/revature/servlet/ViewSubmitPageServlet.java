package com.revature.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/viewSubmitpage")
public class ViewSubmitPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewSubmitPageServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("features/submitReimbursement/submitreimbursement.html").forward(request,
				response);
	}
}

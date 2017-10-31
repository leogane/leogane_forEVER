package com.revature.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/viewPendingpage")
public class ViewPendingPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewPendingPageServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("features/viewReimbursement/viewpending.html").forward(request, response);
	}
}

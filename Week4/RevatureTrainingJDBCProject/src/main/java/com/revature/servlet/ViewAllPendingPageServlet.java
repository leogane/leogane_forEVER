package com.revature.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/viewAllPendingpage")
public class ViewAllPendingPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewAllPendingPageServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("features/viewReimbursement/viewallpending.html").forward(request, response);
	}
}

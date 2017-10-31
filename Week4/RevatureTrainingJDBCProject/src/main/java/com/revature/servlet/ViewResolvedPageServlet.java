package com.revature.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/viewResolvedpage")
public class ViewResolvedPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewResolvedPageServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("features/viewReimbursement/viewressolved.html").forward(request, response);
	}
}

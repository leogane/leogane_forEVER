package com.revature.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.pojo.ErsUser;

@WebServlet("/viewHomepage")
public class HomePageViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HomePageViewServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ErsUser object should be stored in the session at this point
		HttpSession session = request.getSession();
		ErsUser clientUser = (ErsUser) session.getAttribute("user");

		if (clientUser != null) {

			request.getRequestDispatcher("employee.html").forward(request, response);

		} else {
			response.setStatus(418);
		}
	}
}

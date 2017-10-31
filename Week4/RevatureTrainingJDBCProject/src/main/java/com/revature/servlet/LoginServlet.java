package com.revature.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.pojo.ErsUser;
import com.revature.service.Service;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Service service = new Service();
		ErsUser ersUser = new ErsUser(username, password);
		ersUser = service.validateUser(ersUser);
		if (ersUser == null) {
			request.getRequestDispatcher("login.html").forward(request, response);
		}

		HttpSession session = request.getSession();
		session.setAttribute("user", ersUser);
		// 2. go to the homepage
		request.getRequestDispatcher("homepage.html").forward(request, response);
	}
}

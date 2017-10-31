package com.revature.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.pojo.ErsUser;


@WebServlet("/navbar")
public class NavbarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public NavbarServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				HttpSession session = request.getSession();
				ErsUser clientUser = (ErsUser) session.getAttribute("user");

				if (clientUser != null) {
					if (clientUser.getRt_id() == 100) {
						request.getRequestDispatcher("features/navbar/employeenavbar.html").forward(request, response);
					} else {
						request.getRequestDispatcher("features/navbar/managernavbar.html").forward(request, response);
					}
				} else {
					response.setStatus(418);
				}
			}
}

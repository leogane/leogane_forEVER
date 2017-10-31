package com.revature.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dto.ErsUserDTO;
import com.revature.pojo.ErsUser;
import com.revature.service.Service;

@WebServlet("/viewUserInfo")
public class ViewEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewEmployeeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		ErsUser clientUser = (ErsUser) session.getAttribute("user");
		Service service = new Service();

		if (clientUser != null) {
			ErsUserDTO ersUserDTO = service.convertToErsUserDTO(clientUser);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(ersUserDTO);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.write(json);
		} else {
			response.setStatus(418);
		}
	}
}

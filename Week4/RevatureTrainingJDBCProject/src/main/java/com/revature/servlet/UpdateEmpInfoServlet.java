package com.revature.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.pojo.ErsUser;
import com.revature.service.Service;

@WebServlet("/updateEmployeeInfo")
public class UpdateEmpInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateEmpInfoServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> myMap = request.getParameterMap();
		Set<String> txObject = myMap.keySet();
		ObjectMapper jackson = new ObjectMapper();
		Object obj = txObject.toArray()[0];
		HttpSession session = request.getSession();
		ErsUser user = (ErsUser) session.getAttribute("user");
		Service service = new Service();
		ErsUser ersUserUpdate = jackson.readValue(((String) obj), ErsUser.class);
		ersUserUpdate.setErs_id(user.getErs_id());
		ersUserUpdate.setRt_id(user.getRt_id());
		if (service.updateERSUser(ersUserUpdate)) {
			session.setAttribute("user", ersUserUpdate);
		}
	}
}

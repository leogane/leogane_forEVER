package com.revature.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dto.ReimbursementDTO;
import com.revature.pojo.ErsUser;
import com.revature.service.Service;

@WebServlet("/viewPendingRequests")
public class ViewPendingRequestsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViewPendingRequestsServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		ErsUser clientUser = (ErsUser) session.getAttribute("user");
		Service service = new Service();
		if (clientUser != null) {
			List<ReimbursementDTO> pendingReimbursements = service.getPendingReimbursements(clientUser);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(pendingReimbursements);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.write(json);
		} else {
			response.setStatus(418);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String[]> myMap = request.getParameterMap();
		Set<String> txObject = myMap.keySet();
		ObjectMapper jackson = new ObjectMapper();
		Object obj = txObject.toArray()[0];
		ErsUser ersUser = jackson.readValue(((String) obj), ErsUser.class);
		Service service = new Service();
		ersUser = service.getEmployeeInfo(ersUser.getErs_id());
		List<ReimbursementDTO> pendingReimbursements = service.getPendingReimbursements(ersUser);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(pendingReimbursements);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.write(json);
	}
}

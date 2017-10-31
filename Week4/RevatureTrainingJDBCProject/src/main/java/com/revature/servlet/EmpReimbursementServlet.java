package com.revature.servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.revature.dto.ReimbursementRequestDTO;
import com.revature.pojo.ErsUser;
import com.revature.service.Service;

@WebServlet("/processNewReimburse")
public class EmpReimbursementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EmpReimbursementServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();

		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');
			}
		} finally {
			reader.close();
		}

		String in = sb.toString();
		JSONParser parser = new JSONParser();
		JSONObject json2 = null;

		try {
			json2 = (JSONObject) parser.parse(in);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String type = (String) json2.get("reimbursementType");
		double amount = Double.parseDouble((String) json2.get("amount"));
		String description = (String) json2.get("description");
		String receipt = (String) json2.get("receipt");
		ReimbursementRequestDTO reqDto = new ReimbursementRequestDTO(amount, type, description);
		if (receipt != null)
			reqDto.setReceipt(receipt);
		HttpSession session = request.getSession();
		ErsUser user = (ErsUser) session.getAttribute("user");
		Service service = new Service();
		service.submitNewReimbursement(user, reqDto);
	}
}

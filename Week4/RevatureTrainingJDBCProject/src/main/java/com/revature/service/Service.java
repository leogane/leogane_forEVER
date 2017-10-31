package com.revature.service;

import java.util.List;

import com.revature.dao.Dao;
import com.revature.dao.DaoImpl;
import com.revature.dto.ErsUserDTO;
import com.revature.dto.ReimbursementDTO;
import com.revature.dto.ReimbursementRequestDTO;
import com.revature.pojo.ErsUser;
import com.revature.pojo.Reimbursement;
import com.revature.pojo.ReimbursementType;

public class Service {
	Dao dao = new DaoImpl();

	public ErsUser getEmployeeInfo(int userId) {
		ErsUser ersUser = dao.getERSUserById(userId);
		return ersUser;
	}

	public ErsUser validateUser(ErsUser clientUser) {
		ErsUser dbUser = dao.getERSUserByUsername(clientUser.getErs_username());
		if (dbUser != null) {
			if (dbUser.getErs_password().equals(clientUser.getErs_password())) {
				return dbUser;
			}
		}
		return null;
	}

	public ErsUserDTO convertToErsUserDTO(ErsUser clientUser) {
		String roleType = dao.getRoleTypeById(clientUser.getRt_id());
		ErsUserDTO ersUserDto = new ErsUserDTO(clientUser.getErs_id(), clientUser.getErs_fn(),
				clientUser.getErs_ln(), clientUser.getErs_username(), roleType, clientUser.getErs_email());

		return ersUserDto;
	}

	public List<ReimbursementType> getReimbursementTypeList() {

		return dao.getReimbursementTypes();
	}

	public boolean submitNewReimbursement(ErsUser clientUser, ReimbursementRequestDTO request) {
		boolean success = false;
		int typeId = dao.getReimbursementTypeByName(request.getReimbursementType());
		Reimbursement newRequest = new Reimbursement(clientUser.getErs_id(), typeId, request.getAmount(),
				request.getDescription(), null);
		success = dao.submitReimbursementRequest(newRequest);
		return success;
	}

	public List<ReimbursementDTO> getPendingReimbursements(ErsUser ersUser) {
		return dao.getPendingReimbursements(ersUser);
	}

	public List<ReimbursementDTO> getResolvedReimbursements(ErsUser ersUser) {
		return dao.getResolvedReimbursements(ersUser);
	}

	public List<ReimbursementDTO> getAllResolvedReimbursements() {
		return dao.getAllResolvedReimbursements();
	}

	public List<ReimbursementDTO> getAllPendingReimbursements() {
		return dao.getAllPendingReimbursements();
	}

	public List<ErsUser> getAllEmployees() {
		return dao.getAllEmployees();
	}

	public boolean updateERSUser(ErsUser ersUser) {
		return dao.updateERSUser(ersUser, ersUser.getErs_password() != "");
	}

	public Reimbursement getReimbursementById(int reimbursementId) {
		return dao.getReimbursementById(reimbursementId);
	}

	public boolean updateReimbursementRequestStatus(Reimbursement reimbursement) {
		return dao.updateReimbursementRequestStatus(reimbursement);
	}
}

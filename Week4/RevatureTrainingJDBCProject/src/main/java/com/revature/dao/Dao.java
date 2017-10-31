package com.revature.dao;

import java.util.List;

import com.revature.dto.ReimbursementDTO;
import com.revature.pojo.ErsUser;
import com.revature.pojo.Reimbursement;
import com.revature.pojo.ReimbursementType;
import com.revature.pojo.RoleType;
import com.revature.pojo.StatusType;

public interface Dao {
	public ErsUser getERSUserByUsername(String userName);

	public ErsUser getERSUserById(int userId);

	public boolean submitReimbursementRequest(Reimbursement reimbursement);

	public List<ReimbursementDTO> getPendingReimbursements(ErsUser ersUser);

	public List<ReimbursementDTO> getResolvedReimbursements(ErsUser ersUser);

	public boolean updateERSUser(ErsUser ersUser, boolean updatePassword);

	public boolean updateReimbursementRequestStatus(Reimbursement reimbursement);

	public List<ErsUser> getAllEmployees();

	public List<ReimbursementDTO> getAllPendingReimbursements();

	public List<ReimbursementDTO> getAllResolvedReimbursements();

	public boolean addEmployee(ErsUser ersUser);

	public String getRoleTypeById(int roleTypeId);

	public List<ReimbursementType> getReimbursementTypes();

	public int getReimbursementTypeByName(String name);
	
	public Reimbursement getReimbursementById(int reimbursementId);
	
	public ReimbursementDTO getReimbursementDtoById(int reimbursementId);

}

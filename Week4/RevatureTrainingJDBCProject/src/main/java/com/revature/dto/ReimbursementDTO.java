package com.revature.dto;

public class ReimbursementDTO {
	private int reimbursementId;
	private String employeeName;
	private String managerName;
	private String status;
	private String reimbursementType;
	private double amount;
	private String submitted;
	private String resolved;
	private String description;
	private String receipt;

	public ReimbursementDTO() {
		super();
	}

	public ReimbursementDTO(int reimbursementId, String employeeName, String managerName, String status,
			String reimbursementType, double amount, String submitted, String resolved, String description,
			String receipt) {
		super();
		this.reimbursementId = reimbursementId;
		this.employeeName = employeeName;
		this.managerName = managerName;
		this.status = status;
		this.reimbursementType = reimbursementType;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.receipt = receipt;
	}

	public int getReimbursementId() {
		return reimbursementId;
	}

	public void setReimbursementId(int reimbursementId) {
		this.reimbursementId = reimbursementId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReimbursementType() {
		return reimbursementType;
	}

	public void setReimbursementType(String reimbursementType) {
		this.reimbursementType = reimbursementType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getSubmitted() {
		return submitted;
	}

	public void setSubmitted(String submitted) {
		this.submitted = submitted;
	}

	public String getResolved() {
		return resolved;
	}

	public void setResolved(String resolved) {
		this.resolved = resolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	@Override
	public String toString() {
		return "ReimbursementDto [reimbursementId=" + reimbursementId + ", employeeName=" + employeeName
				+ ", managerName=" + managerName + ", status=" + status + ", reimbursementType=" + reimbursementType
				+ ", amount=" + amount + ", submitted=" + submitted + ", resolved=" + resolved + ", description="
				+ description + ", receipt=" + receipt + "]";
	}


}

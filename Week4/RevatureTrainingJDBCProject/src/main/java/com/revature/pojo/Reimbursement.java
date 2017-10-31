package com.revature.pojo;


public class Reimbursement {
	
	private int reimbursementId;
	private int employeeId;
	private int managerId;
	private int statusTypeId;
	private int reimbursementTypeId;
	private double amount;
	private String submitted;
	private String resolved;
	private String description;
	private byte[] receipt;
	
	public Reimbursement() {
		super();
	}
	
	public Reimbursement(int employeeId, int reimbursementTypeId, double amount, String description, byte[] receipt) {
		super();
		this.employeeId = employeeId;
		this.reimbursementTypeId = reimbursementTypeId;
		this.amount = amount;
		this.description = description;
		this.receipt = receipt;
	}

	public Reimbursement(int reimbursementId, int employeeId, int managerId, int statusTypeId, int reimbursementTypeId,
			double amount, String submitted, String resolved, String description, byte[] receipt) {
		super();
		this.reimbursementId = reimbursementId;
		this.employeeId = employeeId;
		this.managerId = managerId;
		this.statusTypeId = statusTypeId;
		this.reimbursementTypeId = reimbursementTypeId;
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

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public int getStatusTypeId() {
		return statusTypeId;
	}

	public void setStatusTypeId(int statusTypeId) {
		this.statusTypeId = statusTypeId;
	}

	public int getReimbursementTypeId() {
		return reimbursementTypeId;
	}

	public void setReimbursementTypeId(int reimbursementTypeId) {
		this.reimbursementTypeId = reimbursementTypeId;
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

	public byte[] getReceipt() {
		return receipt;
	}

	public void setReceipt(byte[] receipt) {
		this.receipt = receipt;
	}

	@Override
	public String toString() {
		return "Reimbursement [reimbursementId=" + reimbursementId + ", employeeId=" + employeeId + ", managerId="
				+ managerId + ", statusTypeId=" + statusTypeId + ", reimbursementTypeId=" + reimbursementTypeId
				+ ", amount=" + amount + ", submitted=" + submitted + ", resolved=" + resolved + ", description="
				+ description + ", receipt=" + receipt + "]";
	}
}

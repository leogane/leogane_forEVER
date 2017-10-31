package com.revature.pojo;

public class ReimbursementType {
	private int reimbursementTypeId;
	private String reimbursementType;

	public ReimbursementType() {
		super();
	}

	public ReimbursementType(int reimbursementTypeId, String reimbursementType) {
		super();
		this.reimbursementTypeId = reimbursementTypeId;
		this.reimbursementType = reimbursementType;
	}

	public int getReimbursementTypeId() {
		return reimbursementTypeId;
	}

	public void setReimbursementTypeId(int reimbursementTypeId) {
		this.reimbursementTypeId = reimbursementTypeId;
	}

	public String getReimbursementType() {
		return reimbursementType;
	}

	public void setReimbursementType(String reimbursementType) {
		this.reimbursementType = reimbursementType;
	}

	@Override
	public String toString() {
		return "ReimbursementType [reimbursementTypeId=" + reimbursementTypeId + ", reimbursementType="
				+ reimbursementType + "]";
	}
}

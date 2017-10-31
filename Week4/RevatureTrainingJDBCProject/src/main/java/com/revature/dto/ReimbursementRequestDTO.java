package com.revature.dto;

public class ReimbursementRequestDTO {
	private double amount;
	private String reimbursementType;
	private String description;
	private String receipt;

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public ReimbursementRequestDTO() {
		super();
	}

	public ReimbursementRequestDTO(double amount, String reimbursementType, String description) {
		super();
		this.amount = amount;
		this.reimbursementType = reimbursementType;
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getReimbursementType() {
		return reimbursementType;
	}

	public void setReimbursementType(String reimbursementType) {
		this.reimbursementType = reimbursementType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ReimbursementRequestDto [amount=" + amount + ", reimbursementType=" + reimbursementType
				+ ", description=" + description + "]";
	}


}

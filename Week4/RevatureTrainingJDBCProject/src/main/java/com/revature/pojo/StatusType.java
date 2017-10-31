package com.revature.pojo;

public class StatusType {
	private int statusTypeId;
	private String statusType;

	public StatusType() {
		super();
	}

	public StatusType(int statusTypeId, String statusType) {
		super();
		this.statusTypeId = statusTypeId;
		this.statusType = statusType;
	}

	public int getStatusTypeId() {
		return statusTypeId;
	}

	public void setStatusTypeId(int statusTypeId) {
		this.statusTypeId = statusTypeId;
	}

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	@Override
	public String toString() {
		return "StatusType [statusTypeId=" + statusTypeId + ", statusType=" + statusType + "]";
	}
}

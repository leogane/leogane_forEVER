package com.revature.pojo;

public class RoleType {
	private int roleTypeId;
	private String roleType;

	public RoleType() {
		super();
	}

	public RoleType(int roleTypeId, String roleType) {
		super();
		this.roleTypeId = roleTypeId;
		this.roleType = roleType;
	}

	public int getRoleTypeId() {
		return roleTypeId;
	}

	public void setRoleTypeId(int roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	@Override
	public String toString() {
		return "RoleType [roleTypeId=" + roleTypeId + ", roleType=" + roleType + "]";
	}
}

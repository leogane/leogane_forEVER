package com.revature.pojo;

public class ErsUser {
	
	 private int ers_id;
	 private String ers_fn;
	 private String ers_ln;
	 private String ers_username;
	 private String ers_password;
	 private int rt_id;
	 private String ers_email;
	 
	 public ErsUser() {
		super();
	}
	public ErsUser(String ers_username, String ers_password) {
		super();
		this.ers_username = ers_username;
		this.ers_password = ers_password;
	}
	
	 
	 public ErsUser(int ers_id, String ers_fn, String ers_ln, String ers_username, String ers_password, int rt_id,
			String ers_email) {
		super();
		this.ers_id = ers_id;
		this.ers_fn = ers_fn;
		this.ers_ln = ers_ln;
		this.ers_username = ers_username;
		this.ers_password = ers_password;
		this.rt_id = rt_id;
		this.ers_email = ers_email;
	}
	public int getErs_id() {
		return ers_id;
	}
	public void setErs_id(int ers_id) {
		this.ers_id = ers_id;
	}
	public String getErs_fn() {
		return ers_fn;
	}
	public void setErs_fn(String ers_fn) {
		this.ers_fn = ers_fn;
	}
	public String getErs_ln() {
		return ers_ln;
	}
	public void setErs_ln(String ers_ln) {
		this.ers_ln = ers_ln;
	}
	public String getErs_username() {
		return ers_username;
	}
	public void setErs_username(String ers_username) {
		this.ers_username = ers_username;
	}
	public String getErs_password() {
		return ers_password;
	}
	public void setErs_password(String ers_password) {
		this.ers_password = ers_password;
	}
	public int getRt_id() {
		return rt_id;
	}
	public void setRt_id(int rt_id) {
		this.rt_id = rt_id;
	}
	public String getErs_email() {
		return ers_email;
	}
	public void setErs_email(String ers_email) {
		this.ers_email = ers_email;
	}
	@Override
	public String toString() {
		return "ErsUser [ers_id=" + ers_id + ", ers_fn=" + ers_fn + ", ers_ln=" + ers_ln + ", ers_username="
				+ ers_username + ", ers_password=" + ers_password + ", rt_id=" + rt_id + ", ers_email=" + ers_email
				+ "]";
	}
}

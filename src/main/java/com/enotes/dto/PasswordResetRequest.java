package com.enotes.dto;

public class PasswordResetRequest {

	private Integer uid;
	
	private String newPassword;

	public PasswordResetRequest() {
		super();
	}

	public PasswordResetRequest(Integer uid, String newPassword) {
		super();
		this.uid = uid;
		this.newPassword = newPassword;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "PasswordResetRequest [uid=" + uid + ", newPassword=" + newPassword + "]";
	}
	
	
}

package com.enotes.dto;

public class PasswordChangeRequest {

	
	private String oldPassword;
	
	private String newPassword;

	public PasswordChangeRequest() {
		super();
	}

	public PasswordChangeRequest(String oldPassword, String newPassword) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "PasswordChangeRequest [oldPassword=" + oldPassword + ", newPassword=" + newPassword + "]";
	}
	
	
	//private constructor for Builder
	private PasswordChangeRequest(Builder builder)
	{
		this.oldPassword = builder.oldPassword;
		this.newPassword = builder.newPassword;
	}
	
	public static Builder builder()
	{
		return new Builder();
	}
	
	public static class Builder
	{
		private String oldPassword;
		
		private String newPassword;
		
		public Builder oldPassword(String oldPassword)
		{
			this.oldPassword = oldPassword;
			return this;
		}
		
		public Builder newPassword(String newPassword)
		{
			this.newPassword = newPassword;
			return this;
		}
		
		public PasswordChangeRequest build()
		{
			return new PasswordChangeRequest(this);
		}
	}
}

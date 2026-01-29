package com.enotes.dto;

import com.enotes.dto.UserRequest.Builder;

public class LoginRequest {

	private String email;
	
	private String password;
	
	

	public LoginRequest() {
		super();
	}

	public LoginRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	@Override
	public String toString() {
		return "LoginRequest [email=" + email + ", password=" + password + "]";
	}
	
	
	//private constructor for Builder
	private LoginRequest(Builder builder)
	{
		this.email = builder.email;
		this.password = builder.password;
		
	}
	
	
	//Static Builder method
	public static Builder builder()
	{
		return new Builder();
	}
	
	//BUILDER Class
	public static class Builder
	{
		private String email;
		private String password;
		
		public Builder email(String email)
		{
			this.email = email;
			return this;
		}
		
		public Builder password(String password)
		{
			this.password = password;
			return this;
		}
		
		public LoginRequest build()
		{
			return new LoginRequest(this);
		}
	}
}

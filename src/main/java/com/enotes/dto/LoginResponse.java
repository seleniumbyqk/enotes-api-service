package com.enotes.dto;

import com.enotes.entity.FavouritNote.Builder;

public class LoginResponse {

	private UserRequest user;
	
	private String token;

	public LoginResponse() {
		super();
	}

	public LoginResponse(UserRequest user, String token) {
		super();
		this.user = user;
		this.token = token;
	}

	public UserRequest getUser() {
		return user;
	}

	public void setUser(UserRequest user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginResponse [user=" + user + ", token=" + token + "]";
	}
	
	//private constructor for builder
	private LoginResponse(Builder builder)
	{
		this.user = builder.user;
		this.token = builder.token;
	}
	
	
	//static method for builder
	public static Builder builder()
	{
		return new Builder();
	}
	
	//Builder class
	public static class Builder{
		
		private UserRequest user;
		
		private String token;
		
		public Builder user(UserRequest user)
		{
			this.user = user;
			return this;
		}
		
		public Builder token(String token)
		{
			this.token = token;
			return this;
		}
		
		public LoginResponse build()
		{
			return new LoginResponse(this);
		}
	}
}

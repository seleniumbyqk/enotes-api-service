package com.enotes.dto;

import com.enotes.entity.FavouritNote.Builder;

public class LoginResponse {

	private UserDto user;
	
	private String token;

	public LoginResponse() {
		super();
	}

	public LoginResponse(UserDto user, String token) {
		super();
		this.user = user;
		this.token = token;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
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
		
		private UserDto user;
		
		private String token;
		
		public Builder user(UserDto user)
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

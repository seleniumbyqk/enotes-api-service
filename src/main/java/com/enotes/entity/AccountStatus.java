package com.enotes.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AccountStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Boolean isActive;
	
	private String verificationCode;

	public AccountStatus() {
		super();
	}

	public AccountStatus(Integer id, Boolean isActive, String verificationCode) {
		super();
		this.id = id;
		this.isActive = isActive;
		this.verificationCode = verificationCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	@Override
	public String toString() {
		return "AccountStatus [id=" + id + ", isActive=" + isActive + ", verificationCode=" + verificationCode + "]";
	}
	
	
	//Private constructor builder
	private AccountStatus(Builder builder)
	{
		this.id = builder.id;
		this.isActive = builder.isActive;
		this.verificationCode = builder.verificationCode;
	}
	
	public static Builder builder()
	{
		return new Builder();
	}
	
	public static class Builder
	{
		private Integer id;
		private Boolean isActive;
		private String verificationCode;
		
		public Builder id(Integer id)
		{
			this.id = id;
			return this;
		}
		
		public Builder isActive(Boolean isActive)
		{
			this.isActive = isActive;
			return this;
		}
		
		public Builder verificationCode(String verificationCode)
		{
			this.verificationCode = verificationCode;
			return this;
		}
		
		public AccountStatus build()
		{
			return new AccountStatus(this);
		}
	}
}

package com.enotes.dto;

import java.util.List;



public class UserResponse {

private Integer id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String mobno;
	
	private StatusDto status;
	
	private List<RoleDto> roles;
	
	
	//No need to create builders for inner classes
	
	public static class StatusDto
	{
		private Integer id;
		
		private String name;

		public StatusDto() {
			super();
		}

		public StatusDto(Integer id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		
		private StatusDto(Builder builder) {
	        this.id = builder.id;
	        this.name = builder.name;
	        
	    }

	    public static Builder builder() {
	        return new Builder();
	    }
	    
	    // ===== BUILDER =====
	    public static class Builder {
	    	
	    	private Integer id;
	    	private String name;
	    	
		public Builder id(Integer id) {
            this.id = id;
            return this;
        }
		
		public Builder name(String name) {
            this.name = name;
            return this;
        }

		 public StatusDto build() {
	            return new StatusDto(this);
	        }
		 
	    }
	    
	    
		
	}
	
	public static class RoleDto
	{
		private Integer id;
		
		private Boolean isActive;

		public RoleDto() {
			super();
		}

		public RoleDto(Integer id, Boolean isActive) {
			super();
			this.id = id;
			this.isActive = isActive;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Boolean getIsactive() {
			return isActive;
		}

		public void setIsActive(Boolean isActive) {
			this.isActive = isActive;
		}
		
		
		private RoleDto(Builder builder) {
	        this.id = builder.id;
	        this.isActive = builder.isActive;
	       
	    }
		
		public static Builder builder() {
	        return new Builder();
	    }
		
		 // ===== BUILDER =====
	    public static class Builder {
	    	
	    	private Integer id;
	    	private Boolean isActive;
	    	
		public Builder id(Integer id) {
            this.id = id;
            return this;
        }
		
		public Builder isactive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

		 public RoleDto build() {
	            return new RoleDto(this);
	        }
		 
	    }
	}

	public UserResponse() {
		super();
	}

	public UserResponse(Integer id, String firstName, String lastName, String email, String mobno, StatusDto status,
			List<RoleDto> roles) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobno = mobno;
		this.status = status;
		this.roles = roles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobno() {
		return mobno;
	}

	public void setMobno(String mobno) {
		this.mobno = mobno;
	}

	public StatusDto getStatus() {
		return status;
	}

	public void setStatus(StatusDto status) {
		this.status = status;
	}

	public List<RoleDto> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserResponse [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", mobno=" + mobno + ", status=" + status + ", roles=" + roles + "]";
	}
	
	
	 private UserResponse(Builder builder) {
	        this.id = builder.id;
	        this.firstName = builder.firstName;
	        this.lastName = builder.lastName;
	        this.email = builder.email;
	        this.mobno = builder.mobno;
	        this.status = builder.status;
	        this.roles = builder.roles;
	    }

	    public static Builder builder() {
	        return new Builder();
	    }
	    
	    
	 // ===== BUILDER =====
	    public static class Builder {

	        private Integer id;
	        private String firstName;
	        private String lastName;
	        private String email;
	        private String mobno;
	        private StatusDto status;
	        private List<RoleDto> roles;

	        public Builder id(Integer id) {
	            this.id = id;
	            return this;
	        }

	        public Builder firstName(String firstName) {
	            this.firstName = firstName;
	            return this;
	        }

	        public Builder lastName(String lastName) {
	            this.lastName = lastName;
	            return this;
	        }

	        public Builder email(String email) {
	            this.email = email;
	            return this;
	        }

	        public Builder mobno(String mobno) {
	            this.mobno = mobno;
	            return this;
	        }

	        public Builder status(StatusDto status) {
	            this.status = status;
	            return this;
	        }

	        public Builder roles(List<RoleDto> roles) {
	            this.roles = roles;
	            return this;
	        }

	        public UserResponse build() {
	            return new UserResponse(this);
	        }
	    }

	    
	

}

package com.enotes.dto;

import java.util.List;

import com.enotes.entity.Role;
import com.enotes.entity.User.Builder;



public class UserDto {

private Integer id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String mobno;
	
	private String password;
	
	private List<RoleDto> roles;
	
	
	
	public UserDto() {
		super();
	}



	public UserDto(Integer id, String firstName, String lastName, String email, String mobno,String password, List<RoleDto> roles) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobno = mobno;
		this.password = password;
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



	public String getMobno() {
		return mobno;
	}



	public void setMobno(String mobno) {
		this.mobno = mobno;
	}



	public List<RoleDto> getRoles() {
		return roles;
	}



	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}




	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
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
		return "UserDto [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", mobno=" + mobno + ", password=" + password + ", roles=" + roles + "]";
	}


	 // Private constructor used by Builder
    private UserDto(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.mobno = builder.mobno;
        this.password = builder.password;
        this.roles = builder.roles;
    }
    
    
 // ---------------- BUILDER ----------------
    public static class Builder {

        private Integer id;
        private String firstName;
        private String lastName;
        private String email;
        private String mobno;
        private String password;
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
        
        public Builder email(String email)
        {
        	this.email = email;
        	return this;
        }

        public Builder mobno(String mobno) {
            this.mobno = mobno;
            return this;
        }
        
        public Builder password(String password)
        {
        	this.password = password;
        	return this;
        }

        public Builder roles(List<RoleDto> roles) {
            this.roles = roles;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }

	public static class RoleDto{
		
		private Integer id;
		
		private String name;

		public RoleDto() {
			super();
		}

		public RoleDto(Integer id, String name) {
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

		@Override
		public String toString() {
			return "RoleDto [id=" + id + ", name=" + name + "]";
		}
		
		  // Private constructor for Builder
	    private RoleDto(Builder builder) {
	        this.id = builder.id;
	        this.name = builder.name;
	    }
	    
	    
	 // ---------------- BUILDER ----------------
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

	        public RoleDto build() {
	            return new RoleDto(this);
	        }
	    }
	}
}

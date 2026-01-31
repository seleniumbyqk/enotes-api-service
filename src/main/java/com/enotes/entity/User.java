package com.enotes.entity;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String mobno;
	
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Role> roles;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "status_id")
	private AccountStatus status;

	public User() {
		super();
	}


	



	public User(Integer id, String firstName, String lastName, String email, String mobno, String password,
			List<Role> roles, AccountStatus status) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobno = mobno;
		this.password = password;
		this.roles = roles;
		this.status = status;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
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

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", mobno=" + mobno + ", password=" + password + ", roles=" + roles + ", status=" + status + "]";
	}
	
	// Private constructor for Builder
    private User(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.mobno = builder.mobno;
        this.password = builder.password;
        this.roles = builder.roles;
        this.status = builder.status;
       
    }
    
    
    // ✅ STATIC BUILDER METHOD (THIS WAS MISSING)
    public static Builder builder() {
        return new Builder();
    }
    
 // ---------------- BUILDER ----------------
    public static class Builder {

        private Integer id;
        private String firstName;
        private String lastName;
        private String email;
        private String mobno;
        private String password;
        private List<Role> roles;
        private AccountStatus status;
       

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

        public Builder roles(List<Role> roles) {
            this.roles = roles;
            return this;
        }
        
       public Builder status(AccountStatus status)
       {
    	   this.status = status;
    	   return this;
       }

        public User build() {
            return new User(this);
        }
    }

	
	
}

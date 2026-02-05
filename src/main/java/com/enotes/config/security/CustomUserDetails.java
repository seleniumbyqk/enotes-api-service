package com.enotes.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.enotes.entity.User;

public class CustomUserDetails implements UserDetails{

	
	private static final long serialVersionUID = 1L;
	
	private final User user;
	
	 public CustomUserDetails(User user) {
	        this.user = Objects.requireNonNull(user, "User must not be null");
	    }
	
	public User getUser() {
		return user;
	}


	/*
	public void setUser(User user) {
		this.user = user;
	}
    */

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		
		
		////////////////////////////
		//List<SimpleGrantedAuthority> authority = new ArrayList<>();
		
		/*
		 //without curley brace
		user.getRoles().forEach(r -> {
			authority.add(new SimpleGrantedAuthority("ROLE_" + r.getName()));   //ROLE_ADMIN
		});
		*/
		
		/*
		user.getRoles().forEach(r -> 
			authority.add(new SimpleGrantedAuthority("ROLE_" + r.getName())));
		
		return authority;
		*/
		/////////////////////////////
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (user.getRoles() != null) {
            user.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()))
            );
        }

        return authorities;
		
		
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

}

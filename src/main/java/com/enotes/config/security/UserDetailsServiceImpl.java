package com.enotes.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.enotes.entity.User;
import com.enotes.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	/*
	//field injection
	@Autowired
	private UserRepository userRepository;
	*/
	
	//Constructor injection
	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user = userRepository.findByEmail(username);
		
		if(user == null)
		{
			throw new UsernameNotFoundException("Invalid email");
		}
		return new CustomUserDetails(user);
	}

}

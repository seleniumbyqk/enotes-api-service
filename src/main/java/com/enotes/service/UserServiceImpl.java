package com.enotes.service;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enotes.dto.PasswordChangeRequest;
import com.enotes.entity.User;
import com.enotes.repository.UserRepository;
import com.enotes.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void changePassword(PasswordChangeRequest passwordChangeRequest) {
		// TODO Auto-generated method stub
		
		//Get id of logged in user
		User loggedInUser = CommonUtil.getLoggedInUser();
		
		//Change password from decode to encode
		//Check old password and password from db are matches
		
		if(!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), loggedInUser.getPassword()))
		{
			throw new IllegalArgumentException("Old password is incorrect");
		}
		
		//New Password encode 
		
		String encodePassword = passwordEncoder.encode(passwordChangeRequest.getNewPassword());
		
		//Set new password
		loggedInUser.setPassword(encodePassword);
		
		//Save in database
		userRepository.save(loggedInUser);
		
		//return true;
	}

	
	
}

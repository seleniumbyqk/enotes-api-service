package com.enotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enotes.entity.AccountStatus;
import com.enotes.entity.User;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.exception.SuccessException;
import com.enotes.repository.UserRepository;

@Service
public class HomeServiceImpl implements HomeService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception {
		// TODO Auto-generated method stub
		
		//Check user id is present or not
		User user = userRepository.findById(userId).orElseThrow(() -> 
		new ResourceNotFoundException("Invalid user id"));
		
		//If verification code is null
		if(user.getStatus().getVerificationCode() == null)
		{
			throw new SuccessException("Account already verified");
		}
		
		//Check verification code matches or not
		if(user.getStatus().getVerificationCode().equals(verificationCode))
		{
			AccountStatus status = user.getStatus();
			
			//If verification done then set
			status.setIsActive(true);
			status.setVerificationCode(null);
			
			//Save in database
			userRepository.save(user);
			
			return true;
			
		}
		
		//If mismatch found
		return false;
	}

}

package com.enotes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.enotes.controller.HomeController;
import com.enotes.entity.AccountStatus;
import com.enotes.entity.User;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.exception.SuccessException;
import com.enotes.repository.UserRepository;

@Service
public class HomeServiceImpl implements HomeService{

	//Logger implementation manually
	Logger log = LoggerFactory.getLogger(HomeController.class);
		
	/*
	//field injection
	@Autowired
	private UserRepository userRepository;
	*/
	
	//constructor injection
	private UserRepository userRepository;
	
	public HomeServiceImpl(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}
	
	@Override
	public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception {
		// TODO Auto-generated method stub
		
		log.info("HomeServiceImpl : verifyAccount() : Start");
		
		//Check user id is present or not
		User user = userRepository.findById(userId).orElseThrow(() -> 
		new ResourceNotFoundException("Invalid user id"));
		
		//If verification code is null
		if(user.getStatus().getVerificationCode() == null)
		{
			log.info("HomeServiceImpl : verifyAccount() : Account already verified");
			
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
			
			log.info("HomeServiceImpl : verifyAccount() : Account verification success");
			
			return true;
			
		}
		
		log.info("HomeServiceImpl : verifyAccount() : End");
		
		//If mismatch found
		return false;
	}

}

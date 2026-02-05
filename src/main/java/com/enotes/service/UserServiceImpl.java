package com.enotes.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.enotes.dto.EmailRequest;
import com.enotes.dto.PasswordChangeRequest;
import com.enotes.dto.PasswordResetRequest;
import com.enotes.entity.User;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.repository.UserRepository;
import com.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService{

	/*
	//field injection
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	*/
	
	//constructor injection
	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	private EmailService emailService;
	
	public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, EmailService emailService)
	{
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.emailService = emailService;
	}
	
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

	@Override
	public void sendEmailForPasswordReset(String email, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
		//Find by email from database
		User user = userRepository.findByEmail(email);
		
		//Check email is available or not
		if(ObjectUtils.isEmpty(user))
		{
			throw new ResourceNotFoundException("Invalid email or email not available");
		}
		
		//Generate unique password reset token
		String passwordResetToken = UUID.randomUUID().toString();
		
		
		//set token
		user.getStatus().setPasswordResetToken(passwordResetToken);
		
		//save in db
		User updateUser = userRepository.save(user);
		
		String url = CommonUtil.getUrl(request);
		
		//EmailRequest emailRequest = sendEmailRequest(updateUser, url);
		
		sendEmailRequest(updateUser, url);
		
		//emailService.sendEmail(emailRequest);
	}
	
	

	private void sendEmailRequest(User user, String url) throws Exception {
		// TODO Auto-generated method stub
		
		String message = "Hi <b>[[username]],</b>"
				+ " <br> <p>You have requested to reset your password</p>"
				+ "<p> Click the below link to change your password </p>"
				+ "<p><a href=[[url]]>Change my password</p>"
				+ "<p>Ignore this email if you do remember your password,"
				+ "or you have not ade the request.</p><br>"
				+ "Thanks, <br> Enotes.com";
		
		message = message.replace("[[username]]", user.getFirstName());
		
		//Dynamic url -1
		//message = message.replace("[[url]]", "http://localhost:8081/api/v1/home/verify?uid=" 
		//+ saveUser.getId() + "&&code=" + saveUser.getStatus().getVerificationCode());
		
		//Dynamic url -2
		message = message.replace("[[url]]", url + "/api/v1/home/verify-password-link?uid=" 
				+ user.getId() + "&&code=" + user.getStatus().getPasswordResetToken());
		
		System.out.println("Dynamic url:"+ url);
		
		EmailRequest emailRequest = EmailRequest.builder()
				.to(user.getEmail())
				.title("Password Reset")
				.subject("Password Reset link")
				.message(message)
				.build();
		
		//send password reset email to user
		emailService.sendEmail(emailRequest);
		
		//return emailRequest;
	}

	@Override
	public void verifyPasswordResetLink(Integer uid, String code) throws Exception {
		// TODO Auto-generated method stub
		
		//User find by id from repository if not found throw exception
		User user = userRepository.findById(uid).orElseThrow(() -> 
		new ResourceNotFoundException("Invalid user or user id"));
		
		verifyPasswordResetToken(user.getStatus().getPasswordResetToken(), code);
	}

	private void verifyPasswordResetToken(String existToken, String reqToken) {
		// TODO Auto-generated method stub
		
		//Check request token is not null
		if(StringUtils.hasText(reqToken))
		{
			
			//If password already reset
			if(!StringUtils.hasText(existToken))
			{
				throw new IllegalArgumentException("Already password reset");
			}
			
			//If both are not equal
			if(!existToken.equals(reqToken))
			{
				throw new IllegalArgumentException("Invalid url");
			}
		}
		else
		{
			//Check request token is null
			throw new IllegalArgumentException("Invalid token or code");
		}
		
	}

	@Override
	public void resetPassword(PasswordResetRequest passwordResetRequest) throws Exception {
		// TODO Auto-generated method stub
		
		//User find by id from repository if not found throw exception
				User user = userRepository.findById(passwordResetRequest.getUid()).orElseThrow(() -> 
				new ResourceNotFoundException("Invalid user or user id"));
				
		//Encrypt new password
		String encodePassword = passwordEncoder.encode(passwordResetRequest.getNewPassword());
		
		//Set new Password
		user.setPassword(encodePassword);
		
		//Set password reset token null
		user.getStatus().setPasswordResetToken(null);
		//Save in db
		userRepository.save(user);
	}

	
	
	
	
}

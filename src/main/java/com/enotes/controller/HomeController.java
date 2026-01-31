package com.enotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.PasswordResetRequest;
import com.enotes.service.HomeService;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid, @RequestParam String code) throws Exception
	{
		Boolean verifyAccount = homeService.verifyAccount(uid, code);
		
		//If true
		if(verifyAccount)
		{
			return CommonUtil.createBuildResponseMessage("Account verification success", HttpStatus.OK);
		}
		
		//If false
		return CommonUtil.createErrorResponseMessage("Invalid verification link", HttpStatus.BAD_REQUEST);
	}
	
	
	//Email for password reset
	@GetMapping("/send-email-reset")
	public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception
	{
		//Call method from user service
		userService.sendEmailForPasswordReset(email,request);
		
		//Success message
		return CommonUtil.createBuildResponseMessage("Email send success !! Check email and reset password", HttpStatus.OK);
	}
	
	//Verify password link
	@GetMapping("/verify-password-link")
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code) throws Exception
	{
		
		//Call method
		userService.verifyPasswordResetLink(uid, code);
		
		return CommonUtil.createBuildResponseMessage("verification success", HttpStatus.OK);
	}
	
	//reset password
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws Exception
	{
		
		//call method
		userService.resetPassword(passwordResetRequest);
		
		//Success message
		return CommonUtil.createBuildResponseMessage("Password reset success", HttpStatus.OK);
	}
}

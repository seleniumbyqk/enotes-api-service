package com.enotes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.PasswordResetRequest;
import com.enotes.endpoint.HomeControllerEndpoint;
import com.enotes.service.HomeService;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
//@RequestMapping("/api/v1/home")
public class HomeController implements HomeControllerEndpoint{

	//Logger implementation manually
	Logger log = LoggerFactory.getLogger(HomeController.class);
	
	/*
	//field injection
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserService userService;
	*/
	
	//constructor injection
	private final HomeService homeService;
	private final UserService userService;
	
	public HomeController(HomeService homeService, UserService userService)
	{
		this.homeService = homeService;
		this.userService = userService;
	}
	
	//@GetMapping("/verify")
	@Override
	public ResponseEntity<?> verifyUserAccount(Integer uid, String code) throws Exception
	{
		log.info("HomeController : verifyUserAccount() :Execution start");
		
		boolean verifyAccount = homeService.verifyAccount(uid, code);
		
		//If true
		if(verifyAccount)
		{
			return CommonUtil.createBuildResponseMessage("Account verification success", HttpStatus.OK);
		}
		
		log.info("HomeController : verifyUserAccount() :Execution end");
		
		//If false
		return CommonUtil.createErrorResponseMessage("Invalid verification link", HttpStatus.BAD_REQUEST);
	}
	
	
	//Email for password reset
	//@GetMapping("/send-email-reset")
	@Override
	public ResponseEntity<?> sendEmailForPasswordReset(String email, HttpServletRequest request) throws Exception
	{
		//Call method from user service
		userService.sendEmailForPasswordReset(email,request);
		
		//Success message
		return CommonUtil.createBuildResponseMessage("Email send success !! Check email and reset password", HttpStatus.OK);
	}
	
	//Verify password link
	//@GetMapping("/verify-password-link")
	@Override
	public ResponseEntity<?> verifyPasswordResetLink(Integer uid, String code) throws Exception
	{
		
		//Call method
		userService.verifyPasswordResetLink(uid, code);
		
		return CommonUtil.createBuildResponseMessage("verification success", HttpStatus.OK);
	}
	
	//reset password
	//@PostMapping("/reset-password")
	@Override
	public ResponseEntity<?> resetPassword(PasswordResetRequest passwordResetRequest) throws Exception
	{
		
		//call method
		userService.resetPassword(passwordResetRequest);
		
		//Success message
		return CommonUtil.createBuildResponseMessage("Password reset success", HttpStatus.OK);
	}
}

package com.enotes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.LoginRequest;
import com.enotes.dto.LoginResponse;
import com.enotes.dto.UserRequest;
import com.enotes.endpoint.AuthControllerEndpoint;
import com.enotes.service.AuthService;
import com.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
//@RequestMapping("/api/v1/auth")
public class AuthController implements AuthControllerEndpoint{

	Logger log = LoggerFactory.getLogger(AuthController.class);
	
	//Auth + User Controller
	
	/*
	//Filed constructor
	@Autowired
	private AuthService userService;
	*/
	
	//Constructor injection
	private final AuthService userService;
	
	public AuthController(AuthService userService)
	{
		this.userService = userService;
	}
	
	/*
	//Dynamic url 1
	@PostMapping("/")
	public ResponseEntity<?> registerUser(@RequestBody UserRequest userDto) throws Exception
	{
		//Call register method from User service
		Boolean register = userService.register(userDto);
		
		//Check true or false and throw error
		if(register)
		{
			return CommonUtil.createBuildResponseMessage("Register success", HttpStatus.CREATED);
		}
		
		//If false
		return CommonUtil.createErrorResponseMessage("Register failed", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	*/
	
	//Dynamic url 2
	//@PostMapping("/register")
	@Override
	public ResponseEntity<?> registerUser(UserRequest userDto, HttpServletRequest request) throws Exception
	{
		log.info("AuthController : registerUser() : Execution Start");
		
		//Fully dynamic url
		 String url = CommonUtil.getUrl(request);
		
		
		//Call register method from User service
		boolean register = userService.register(userDto, url);
		
		//Check true or false and throw error
		if(!register)
		{
			log.info("AuthController : registerUser() : Register Failed");
			
			//If false
			return CommonUtil.createErrorResponseMessage("Register failed", HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		log.info("AuthController : registerUser() : Execution End");
		
		return CommonUtil.createBuildResponseMessage("Register success", HttpStatus.CREATED);
	}
	
	
	//@PostMapping("/login")
	@Override
	public ResponseEntity<?> login(LoginRequest loginRequest)
	{
		
		LoginResponse loginResponse = userService.login(loginRequest);
		
		if(ObjectUtils.isEmpty(loginResponse))
		{
			return CommonUtil.createErrorResponseMessage("Invalid credentials", HttpStatus.BAD_REQUEST);
		}
		
		return CommonUtil.createBuildResponse(loginResponse, HttpStatus.OK);
	}
}

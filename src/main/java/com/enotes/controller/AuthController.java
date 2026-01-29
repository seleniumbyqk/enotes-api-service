package com.enotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.LoginRequest;
import com.enotes.dto.LoginResponse;
import com.enotes.dto.UserRequest;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	
	//Auth + User Controller
	
	@Autowired
	private UserService userService;
	
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
	@PostMapping("/")
	public ResponseEntity<?> registerUser(@RequestBody UserRequest userDto, HttpServletRequest request) throws Exception
	{
		//Fully dynamic url
		 String url = CommonUtil.getUrl(request);
		
		
		//Call register method from User service
		Boolean register = userService.register(userDto, url);
		
		//Check true or false and throw error
		if(register)
		{
			return CommonUtil.createBuildResponseMessage("Register success", HttpStatus.CREATED);
		}
		
		//If false
		return CommonUtil.createErrorResponseMessage("Register failed", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)
	{
		
		LoginResponse loginResponse = userService.login(loginRequest);
		
		if(ObjectUtils.isEmpty(loginResponse))
		{
			return CommonUtil.createErrorResponseMessage("Invalid credentials", HttpStatus.BAD_REQUEST);
		}
		
		return CommonUtil.createBuildResponse(loginResponse, HttpStatus.OK);
	}
}

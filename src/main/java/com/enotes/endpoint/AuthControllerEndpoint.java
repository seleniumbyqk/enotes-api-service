package com.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enotes.dto.LoginRequest;
import com.enotes.dto.UserRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jfr.Percentage;

//Swagger tags
@Tag(name = "Authentication", description = "All the User Authentication APIs")
@RequestMapping("/api/v1/auth")
public interface AuthControllerEndpoint {

	//No need to mention @RequestBody, @PathVariable, @RequestParam in implementation classes
	
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description =  "Register Success"),
			@ApiResponse(responseCode = "500", description =  "Internal Server Error"),
			@ApiResponse(responseCode = "400", description =  "Bad Request")
			})
	@Operation(summary = "User Register Endpoint", tags = {"Authentication", "Home"})
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserRequest userDto, HttpServletRequest request) throws Exception;
	
	@Operation(summary = "User Login Endpoint", tags = {"Authentication"})
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest);
	
	
}

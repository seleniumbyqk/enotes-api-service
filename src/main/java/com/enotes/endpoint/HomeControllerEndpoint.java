package com.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.enotes.dto.PasswordResetRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Home", description = "All the Home APIs")
@RequestMapping("/api/v1/home")
public interface HomeControllerEndpoint {

	//No need to mention @RequestBody, @PathVariable, @RequestParam in implementation classes
	
	@Operation(summary = "Verification User Account Endpoint", tags = {"Home"}, description = "User Account Verification after Register Account")
	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid, @RequestParam String code) throws Exception;
	
	//Email for password reset
	@Operation(summary = "Send Email For Password Reset Endpoint", tags = {"Home"}, description = "User Can Send Email For Password Reset")
	@GetMapping("/send-email-reset")
	public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception;
	
	//Verify password link
	@Operation(summary = "Verification Password Link Endpoint", tags = {"Home"}, description = "User Verification Password Link")
	@GetMapping("/verify-password-link")
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code) throws Exception;
	
	//reset password
	@Operation(summary = "Reset Password Endpoint", tags = {"Home"}, description = "User can changes Password")
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws Exception;
	
	
}

package com.enotes.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import com.enotes.config.security.CustomUserDetails;
import com.enotes.entity.User;
import com.enotes.handler.GenericResponse;

import jakarta.servlet.http.HttpServletRequest;



public class CommonUtil {


	public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status)
	{
		GenericResponse response = GenericResponse.builder()
				.responseStatus(status)
				.status("success")
				.message("success")
				.data(data)
				.build();

		return response.create();
	}


	public static ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus status)
	{
		GenericResponse response = GenericResponse.builder()
				.responseStatus(status)
				.status("success")
				.message(message)
				.build();

		return response.create();
	}


	public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus status)
	{
		GenericResponse response = GenericResponse.builder()
				.responseStatus(status)
				.status("failed")
				.message("failed")
				.data(data)
				.build();

		return response.create();
	}


	public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus status)
	{
		GenericResponse response = GenericResponse.builder()
				.responseStatus(status)
				.status("failed")
				.message(message)
				.build();

		return response.create();
	}


	public static String getContentType(String originalFileName) {
		// TODO Auto-generated method stub

		String extension = FilenameUtils.getExtension(originalFileName);

		switch(extension)
		{
		case "pdf":
			return "application/pdf";

		case "xlsx":
			return "application/vnd.openxmlformats-officedocument.spreadsheettml.sheet";

		case "txt":
			return "text/plain";

		case "png":
			return "image/png";

		case "jpg":
		case "jpeg":
			return "image/jpeg";

			default:
				return "application/octet-stream";
		}


	}


	public static String getUrl(HttpServletRequest request) {
		// TODO Auto-generated method stub

		//Get api url
		//http://localhost:8081/api/v1/auth
		String apiUrl = request.getRequestURL().toString();

		// /api/v1/auth
		//String url = request.getServletPath();

		//http://localhost:8081
		apiUrl = apiUrl.replace(request.getServletPath(), "");

		System.out.println("Api Url:" + apiUrl);

		return apiUrl;
	}


	public static User getLoggedInUser()
	{
		
		CustomUserDetails logUser = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();


		return logUser.getUser();
		

		/*
		Authentication authentication =
	            SecurityContextHolder.getContext().getAuthentication();

	    if (authentication == null ||
	        !authentication.isAuthenticated() ||
	        authentication.getPrincipal() == null ||
	        authentication.getPrincipal() instanceof String) {
	        return null; // or throw custom exception
	    }

	    CustomUserDetails logUser =
	            (CustomUserDetails) authentication.getPrincipal();

	    return logUser.getUser();
	    
	    */
	}
	
}

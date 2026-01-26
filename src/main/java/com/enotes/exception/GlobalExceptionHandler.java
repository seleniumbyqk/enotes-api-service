package com.enotes.exception;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.enotes.util.CommonUtil;


@ControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e)
	{
		//return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(SuccessException.class)
	public ResponseEntity<?> handleSuccessException(SuccessException e)
	{
		//return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		
		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.OK);
	}
	
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointerException(Exception e)
	{
		//return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e)
	{
		//return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(Exception e)
	{
		//return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
	{
		
		
		//Show error on particular field
		List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
		
		Map<String, Object> errors = new LinkedHashMap<>();
		
		allErrors.stream().forEach(error ->{
				
				String msg = error.getDefaultMessage();
				
				String field = ((FieldError)(error)).getField();
				
				errors.put(field, msg);
				
				//errors.put(((FieldError) error).getField(), error.getDefaultMessage());
		});
		
//		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException e)
	{
		//return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
		
		return CommonUtil.createErrorResponse(e.getErrors(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?> handleExistDataException(ExistDataException e)
	{
		//return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		
		return CommonUtil.createErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e)
	{
		//return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		
		return CommonUtil.createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e)
	{
		//return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		
		return CommonUtil.createErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e)
	{
		//return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		
		return CommonUtil.createErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
}

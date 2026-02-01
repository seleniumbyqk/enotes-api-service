package com.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.enotes.util.Constants.*;
import com.enotes.dto.CategoryDto;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.util.Constants;

import jakarta.validation.Valid;

@RequestMapping("/api/v1/category")
public interface CategoryControllerEndpoint {


	//No need to mention @RequestBody, @PathVariable, @RequestParam in implementation classes
	
	@PostMapping("/save")
	//@PreAuthorize("hasRole('ADMIN')")         //Only admin can authorize
	//@PreAuthorize(Constants.ROLE_ADMIN)
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDto categoryDto);
	
	@GetMapping("/")
	//@PreAuthorize("hasRole('ADMIN')") 
	//@PreAuthorize(Constants.ROLE_ADMIN)
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllCategory();
	
	
	@GetMapping("/active")
	//@PreAuthorize("hasRole('USER')")   //Only for user
	//@PreAuthorize("hasAnyRole('USER', 'ADMIN')")   //Multiple role
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> getActiveCategory();
	
	@GetMapping("/{id}")
	//@PreAuthorize("hasRole('ADMIN')") 
	//@PreAuthorize(Constants.ROLE_ADMIN)
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws ResourceNotFoundException;
	
	@DeleteMapping("/{id}")
	//@PreAuthorize("hasRole('ADMIN')") 
	//@PreAuthorize(Constants.ROLE_ADMIN)
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> deleteCategoryDetailsById(@PathVariable Integer id);
	
	
}

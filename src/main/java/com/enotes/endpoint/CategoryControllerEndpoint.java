package com.enotes.endpoint;

import static com.enotes.util.Constants.ROLE_ADMIN;
import static com.enotes.util.Constants.ROLE_ADMIN_USER;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enotes.dto.CategoryDto;
import com.enotes.exception.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Category", description = "All the Category Operation APIs")
@RequestMapping("/api/v1/category")
public interface CategoryControllerEndpoint {


	//No need to mention @RequestBody, @PathVariable, @RequestParam in implementation classes
	
	//Swagger tags
	@Operation(summary = "Save Category Endpoint", tags = {"Category"}, description = "Admin Save Category")
	@PostMapping("/save")
	//@PreAuthorize("hasRole('ADMIN')")         //Only admin can authorize
	//@PreAuthorize(Constants.ROLE_ADMIN)
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDto categoryDto);
	
	@Operation(summary = "Get All Category Endpoint", tags = {"Category"}, description = "Admin Get All Category")
	@GetMapping("/")
	//@PreAuthorize("hasRole('ADMIN')") 
	//@PreAuthorize(Constants.ROLE_ADMIN)
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllCategory();
	
	@Operation(summary = "Get Active Category Endpoint", tags = {"Category"}, description = "Admin, User Get Active Category")
	@GetMapping("/active")
	//@PreAuthorize("hasRole('USER')")   //Only for user
	//@PreAuthorize("hasAnyRole('USER', 'ADMIN')")   //Multiple role
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> getActiveCategory();
	
	@Operation(summary = "Get Category By Id Endpoint", tags = {"Category"}, description = "Admin Get Category Details")
	@GetMapping("/{id}")
	//@PreAuthorize("hasRole('ADMIN')") 
	//@PreAuthorize(Constants.ROLE_ADMIN)
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws ResourceNotFoundException;
	
	@Operation(summary = "Delete Category By Id Endpoint", tags = {"Category"}, description = "Admin Delete Category Details")
	@DeleteMapping("/{id}")
	//@PreAuthorize("hasRole('ADMIN')") 
	//@PreAuthorize(Constants.ROLE_ADMIN)
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> deleteCategoryDetailsById(@PathVariable Integer id);
	
	
}

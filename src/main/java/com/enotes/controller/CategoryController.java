package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
import com.enotes.entity.Category;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	/*
	//Without DTO
	@PostMapping("/save-category")
	public ResponseEntity<?> saveCategory(@RequestBody Category category)
	{
		boolean saveCategory = categoryService.saveCategory(category);
		
		if(saveCategory)
		{
			return new ResponseEntity<>("saved success", HttpStatus.CREATED);
		}
		else
		{
			return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	*/
	
	
	//With DTO
	@PostMapping("/save")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto)
	{
		//String nm = null;
		//nm.toUpperCase();
		boolean saveCategory = categoryService.saveCategory(categoryDto);
		
		if(saveCategory)
		{
			return new ResponseEntity<>("saved success", HttpStatus.CREATED);
		}
		else
		{
			return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/*
	//Without DTO
	@GetMapping("/category")
	public ResponseEntity<?> getAllCategory()
	{
		List<Category> allCategory = categoryService.getAllCategory();
		
		if(CollectionUtils.isEmpty(allCategory))
		{
			return ResponseEntity.noContent().build();
		}
		else
		{
			return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}
	}
	*/
	
	
	//With DTO - All Category
	@GetMapping("/")
	public ResponseEntity<?> getAllCategory()
	{
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		
		if(CollectionUtils.isEmpty(allCategory))
		{
			return ResponseEntity.noContent().build();
		}
		else
		{
			return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}
	}
	
	//With DTO - All Category from category response
		@GetMapping("/active")
		public ResponseEntity<?> getActiveCategory()
		{
			List<CategoryResponse> allCategory = categoryService.getActiveCategory();
			
			if(CollectionUtils.isEmpty(allCategory))
			{
				return ResponseEntity.noContent().build();
			}
			else
			{
				return new ResponseEntity<>(allCategory, HttpStatus.OK);
			}
		}
	
		
		@GetMapping("/{id}")
		public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws ResourceNotFoundException
		{
			//When we will use try-catch block then custom exception will run otherwise 
			//global exception handler will run
			
			//Without exception
			CategoryDto categoryDto = categoryService.getCategoryById(id);
			
			if(ObjectUtils.isEmpty(categoryDto))
			{
				//return new ResponseEntity<>("Category not found with id " + id, HttpStatus.NOT_FOUND);
				return new ResponseEntity<>("Internal Server Error", HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<>(categoryDto, HttpStatus.OK);
			
			
			/*
			//With exception
			try
			{
				CategoryDto categoryDto = categoryService.getCategoryById(id);
				
				if(ObjectUtils.isEmpty(categoryDto))
				{
					return new ResponseEntity<>("Category not found with id " + id, HttpStatus.NOT_FOUND);
				}
				
				return new ResponseEntity<>(categoryDto, HttpStatus.OK);
			}
			catch(ResourceNotFoundException e)
			{
				return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
			}
			catch(Exception e)
			{
				return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			*/
		}
		
		
		@DeleteMapping("/{id}")
		public ResponseEntity<?> deleteCategoryDetailsById(@PathVariable Integer id)
		{
			boolean deleted = categoryService.deleteCategoryDetailsById(id);
			
			if(deleted)
			{
				return new ResponseEntity<>("Category deleted success", HttpStatus.OK);
			}
			
			return new ResponseEntity<>("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
		}
}

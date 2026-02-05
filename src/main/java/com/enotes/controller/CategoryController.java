package com.enotes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
import com.enotes.endpoint.CategoryControllerEndpoint;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.service.CategoryService;
import com.enotes.util.CommonUtil;

@RestController
//@RequestMapping("/api/v1/category")
public class CategoryController implements CategoryControllerEndpoint{

	/*
	//field constructor
	@Autowired
	private CategoryService categoryService;
	*/
	
	//COnstructor injection
	private final CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService)
	{
		this.categoryService = categoryService;
	}
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
	//@PostMapping("/save")
	//@PreAuthorize("hasRole('ADMIN')")         //Only admin can authorize
	@Override
	public ResponseEntity<?> saveCategory(CategoryDto categoryDto)
	{
		//String nm = null;
		//nm.toUpperCase();
		boolean saveCategory = categoryService.saveCategory(categoryDto);
		
		if(saveCategory)
		{
			//Generic response
			return CommonUtil.createBuildResponseMessage("Saved success", HttpStatus.CREATED);
			
			//Response by response entity
			//return new ResponseEntity<>("saved success", HttpStatus.CREATED);
		}
		else
		{
			return CommonUtil.createErrorResponseMessage("Category Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
			
			//return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
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
	//@GetMapping("/")
	//@PreAuthorize("hasRole('ADMIN')") 
	@Override
	public ResponseEntity<?> getAllCategory()
	{
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		
		if(CollectionUtils.isEmpty(allCategory))
		{
			return ResponseEntity.noContent().build();
		}
		else
		{
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
			
			//return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}
	}
	
	//With DTO - All Category from category response
		//@GetMapping("/active")
		//@PreAuthorize("hasRole('USER')")   //Only for user
		//@PreAuthorize("hasAnyRole('USER', 'ADMIN')")   //Multiple role
	    @Override
		public ResponseEntity<?> getActiveCategory()
		{
			List<CategoryResponse> allCategory = categoryService.getActiveCategory();
			
			if(CollectionUtils.isEmpty(allCategory))
			{
				return ResponseEntity.noContent().build();
			}
			else
			{
				//return new ResponseEntity<>(allCategory, HttpStatus.OK);
				
				return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
			}
		}
	
		
		//@GetMapping("/{id}")
		//@PreAuthorize("hasRole('ADMIN')") 
		@Override
		public ResponseEntity<?> getCategoryDetailsById(Integer id) throws ResourceNotFoundException
		{
			//When we will use try-catch block then custom exception will run otherwise 
			//global exception handler will run
			
			//Without exception
			CategoryDto categoryDto = categoryService.getCategoryById(id);
			
			if(ObjectUtils.isEmpty(categoryDto))
			{
				//return new ResponseEntity<>("Category not found with id " + id, HttpStatus.NOT_FOUND);
				//return new ResponseEntity<>("Internal Server Error", HttpStatus.NOT_FOUND);
				
				return CommonUtil.createErrorResponseMessage("Internal Server Error", HttpStatus.NOT_FOUND);
			}
			
			//return new ResponseEntity<>(categoryDto, HttpStatus.OK);
			
			return CommonUtil.createBuildResponse(categoryDto, HttpStatus.OK);
			
			
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
		
		
		//@DeleteMapping("/{id}")
		//@PreAuthorize("hasRole('ADMIN')") 
		@Override
		public ResponseEntity<?> deleteCategoryDetailsById(Integer id)
		{
			boolean deleted = categoryService.deleteCategoryDetailsById(id);
			
			if(deleted)
			{
				//return new ResponseEntity<>("Category deleted success", HttpStatus.OK);
				
				return CommonUtil.createBuildResponse("Category deleted success", HttpStatus.OK);
			}
			
			//return new ResponseEntity<>("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
			
			return CommonUtil.createErrorResponseMessage("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
		}
}

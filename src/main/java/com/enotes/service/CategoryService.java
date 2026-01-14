package com.enotes.service;

import java.util.List;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
import com.enotes.entity.Category;

public interface CategoryService {

	
	//Without DTO
	//Save Category into database
	//public boolean saveCategory(Category category);
	
	//Get All categories from database
	//public List<Category> getAllCategory();
	
	//WithDTO
	public boolean saveCategory(CategoryDto categoryDto);
	
	public List<CategoryDto> getAllCategory();

	public List<CategoryResponse> getActiveCategory();
}

package com.enotes.service;

import java.util.List;

import com.enotes.entity.Category;

public interface CategoryService {

	//Save Category into database
	public boolean saveCategory(Category category);
	
	//Get All categories from database
	public List<Category> getAllCategory();
}

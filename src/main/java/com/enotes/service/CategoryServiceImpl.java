package com.enotes.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.entity.Category;
import com.enotes.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public boolean saveCategory(Category category) {
		// TODO Auto-generated method stub
		
		category.setIsDeleted(false);
		
		category.setCreatedBy(1);
		
		category.setCreatedOn(new Date());
		
		Category saveCategory = categoryRepository.save(category);
		
		if(ObjectUtils.isEmpty(saveCategory))
		{
			return false;
		}
		
		return true;
	}

	@Override
	public List<Category> getAllCategory() {
		// TODO Auto-generated method stub
		
		List<Category> categories = categoryRepository.findAll();
		
		return categories;
	}

}

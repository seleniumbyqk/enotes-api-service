package com.enotes.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
import com.enotes.entity.Category;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.repository.CategoryRepository;
import com.enotes.util.Validation;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private Validation validation;
	
	/*
	//Without DTO
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
	*/
	
	
	//WIth DTO
	@Override
	public boolean saveCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		
		
		//Custom value addition
		//Category category = new Category();
		
		//category.setName(categoryDto.getName());
		//category.setDescription(categoryDto.getDescription());
		//category.setIsActive(categoryDto.getIsActive());
		
		//Validation Checking before mapper means converting from categoryDto to category
		validation.categoryValidation(categoryDto);
		
		//Remove above line with model mapper
		Category category = modelMapper.map(categoryDto, Category.class);
		
		if(ObjectUtils.isEmpty(category.getId()))
		{
			category.setIsDeleted(false);
			
			category.setCreatedBy(1);
			
			category.setCreatedOn(new Date());
		}
		else
		{
			updateCategory(category);
		}
		
		
		
		Category saveCategory = categoryRepository.save(category);
		
		if(ObjectUtils.isEmpty(saveCategory))
		{
			return false;
		}
		
		return true;
	}
	
	
	private void updateCategory(Category category) {
		// TODO Auto-generated method stub
		
		Optional<Category> findById = categoryRepository.findById(category.getId());
		
		if(findById.isPresent())
		{
			
			//Don't change this values while updating
			Category existCategory = findById.get();
			
			category.setCreatedBy(existCategory.getCreatedBy());
			
			category.setCreatedOn(existCategory.getCreatedOn());
			
			category.setIsDeleted(existCategory.getIsDeleted());
			
			category.setUpdatedBy(1);
			
			category.setUpdatedOn(new Date());
		}
		
	}


	/*
	//Without DTO
	@Override
	public List<Category> getAllCategory() {
		// TODO Auto-generated method stub
		
		List<Category> categories = categoryRepository.findAll();
		
		return categories;
	}
	*/
	
	
	//With DTO
	@Override
	public List<CategoryDto> getAllCategory() {
		// TODO Auto-generated method stub
		
		//It will show all category including deleted category
		//List<Category> categories = categoryRepository.findAll();
		
		//It will show all category except deleted category
		List<Category> categories = categoryRepository.findByIsDeletedFalse();
		
		//Convert from category to CategoryDto in list
		List<CategoryDto> categoryDtoList = categories.stream().map(cat -> modelMapper.map(cat, CategoryDto.class)).toList();
		
		return categoryDtoList;
	}


	@Override
	public List<CategoryResponse> getActiveCategory() {
		// TODO Auto-generated method stub
		
		//It will show all active categories including deleted category
		//List<Category> categories = categoryRepository.findByIsActiveTrue();
		
		//It will show all categories except deleted category
		List<Category> categories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
		
		List<CategoryResponse> categoryList = categories.stream().map(cat -> modelMapper.map(cat, CategoryResponse.class)).toList();
		
		return categoryList;
	}


	@Override
	public CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		
		//It will show all categories including deleted
		//Optional<Category> findByCategory = categoryRepository.findById(id);
		
		//It will show all categories except deleted
		//Without exception
		//Optional<Category> findByCategory = categoryRepository.findByIdAndIsDeletedFalse(id);
				
		
		//With exception handling
		Category category = categoryRepository.findByIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
		
		
		/*
		//Without exception
		if(findByCategory.isPresent())
		{
			Category category = findByCategory.get();
			
			return modelMapper.map(category, CategoryDto.class);
		}
		*/
		
		
		/////////////////////////////////////////////
		
		//With exception
		
		if(!ObjectUtils.isEmpty(category))
		{
			/*
			if(category.getName() == null)
			{
				throw new IllegalArgumentException("Name is null");
			}
			*/
			
			category.getName().toUpperCase();
			
			return modelMapper.map(category, CategoryDto.class);
		}
		
		
		//return modelMapper.map(category, CategoryDto.class);
		
		return null;
		
		//////////////////////////////////////////////
		
	}


	@Override
	public boolean deleteCategoryDetailsById(Integer id) {
		// TODO Auto-generated method stub
		
Optional<Category> findByCategory = categoryRepository.findById(id);
		
		if(findByCategory.isPresent())
		{
			Category category = findByCategory.get();
			
			category.setIsDeleted(true);
			
			categoryRepository.save(category);
			
			return true;
		}
		
		return false;
	}


	

}

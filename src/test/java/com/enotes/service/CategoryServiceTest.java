package com.enotes.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.enotes.dto.CategoryDto;
import com.enotes.entity.Category;
import com.enotes.exception.ExistDataException;
import com.enotes.repository.CategoryRepository;
import com.enotes.util.Validation;

@ExtendWith(MockitoExtension.class)   //Write test case with mock object and notwith real db
public class CategoryServiceTest {

	@Mock
	private CategoryRepository categoryRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private Validation validation;
	
	@InjectMocks
	private CategoryServiceImpl categoryServiceImpl;
	
	private CategoryDto categoryDto = null;
	private Category category = null;
	private List<Category> categories = new ArrayList<>();
	private List<CategoryDto> categoriesDtos = new ArrayList<>();
	
	@BeforeEach
	public void initialize()
	{
		 categoryDto = CategoryDto.builder()
				    .id(null)
					.name("Java Language Notes")
					.description("This is java language")
					.isActive(true)
					.isDeleted(false)
					.build();
		 
		 category = Category.builder()
				 .id(null)
				 .name("Java Notes")
				 .description("Java Notes")
				 .isActive(true)
				 .isDeleted(false)
				 .build();
		 
		 categories.add(category);
		 categoriesDtos.add(categoryDto);
		 
		 //category = modelMapper.map(categoryDto, Category.class);
	}
	
	@Test
	public void testSaveCategory()
	{
		//arrange
		
		when(categoryRepository.existsByName(categoryDto.getName())).thenReturn(false);
		
		when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
		
		when(categoryRepository.save(category)).thenReturn(category);
		
		//act
		Boolean saveCategory = categoryServiceImpl.saveCategory(categoryDto);
		
		//assert
		Assertions.assertTrue(saveCategory);
		
		//verify
		verify(validation).categoryValidation(categoryDto);
		verify(categoryRepository).existsByName(categoryDto.getName());
		verify(categoryRepository).save(category);
	}
	
	@Test
	public void testCategoryExist()
	{
		//arrange
		when(categoryRepository.existsByName(categoryDto.getName())).thenReturn(true);
		
		//assert
		ExistDataException exception = Assertions.assertThrows(ExistDataException.class, () -> {
			categoryServiceImpl.saveCategory(categoryDto);
		});
		
		Assertions.assertEquals("Category already exists.", exception.getMessage());
		
		//verify
		verify(validation).categoryValidation(categoryDto);
		
		verify(categoryRepository).existsByName(categoryDto.getName());
		
		verify(categoryRepository, never()).save(category);
	}
	
	@Test
	public void testUpdateCategory()
	{
		categoryDto.setId(15);
		category.setId(15);
		
		//arrange
		
		when(categoryRepository.existsByName(categoryDto.getName())).thenReturn(false);
		
		when(modelMapper.map(categoryDto, Category.class)).thenReturn(category);
		
		when(categoryRepository.save(category)).thenReturn(category);
		
		//act
		Boolean saveCategory = categoryServiceImpl.saveCategory(categoryDto);
		
		//assert
		Assertions.assertTrue(saveCategory);
		
		//verify
		verify(validation).categoryValidation(categoryDto);
		verify(categoryRepository).existsByName(categoryDto.getName());
		verify(categoryRepository).save(category);
	}
	
	
	@Test
	public void testGetAllCategory()
	{
		//arrange
		when(categoryRepository.findByIsDeletedFalse()).thenReturn(categories);
		
		List<CategoryDto> allCategory = categoryServiceImpl.getAllCategory();
		
		//assert
		Assertions.assertEquals(allCategory.size(), categories.size());
		
		//verify
		verify(categoryRepository).findByIsDeletedFalse();
	}
}

package com.enotes.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.TodoDto;
import com.enotes.dto.TodoDto.StatusDto;
import com.enotes.enums.TodoStatus;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.exception.ValidationException;

@Component
public class Validation {

	public void categoryValidation(CategoryDto categoryDto)
	{
		Map<String, Object> errors = new LinkedHashMap<>();
		
		if(ObjectUtils.isEmpty(categoryDto))
		{
			throw new IllegalArgumentException("Category Object/JSON should not be null or empty");
		}
		else
		{
			//Name field validation
			
			if(ObjectUtils.isEmpty(categoryDto.getName()))
			{
				//throw new IllegalArgumentException("Name field is Empty or Null");
				errors.put("name:", "Name field is Empty or Null");
			}
			else
			{
				if(categoryDto.getName().length()<10)
				{
					errors.put("name:", "Name Length should be min 10");
				}
				
				if(categoryDto.getName().length()>100)
				{
					errors.put("name:", "Name Length should be max 100");
				}
			}
			
			
			//Description field validation
			if(ObjectUtils.isEmpty(categoryDto.getDescription()))
			{
				//throw new IllegalArgumentException("Name field is Empty or Null");
				errors.put("description:", "Description field is Empty or Null");
			}
			else
			{
				if(categoryDto.getDescription().length()<10)
				{
					errors.put("description:", "Description Length should be min 10");
				}
				
				if(categoryDto.getDescription().length()>100)
				{
					errors.put("description:", "Description Length should be max 100");
				}
			}
			
			
			//isActive field validation
			if(ObjectUtils.isEmpty(categoryDto.getIsActive()))
			{
				//throw new IllegalArgumentException("Name field is Empty or Null");
				errors.put("isActive:", "isActive field is Empty or Null");
			}
			else
			{
				if(categoryDto.getIsActive() != Boolean.TRUE.booleanValue() && categoryDto.getIsActive() != Boolean.FALSE.booleanValue())
				{
					errors.put("isActive:", "Invalid value of isActive field");
				}
			}
		}
		
		if(!errors.isEmpty())
		{
			throw new ValidationException(errors);
		}
	}
	
	
	public void todoValidation(TodoDto todo) throws Exception
	{
		StatusDto reqStatus = todo.getStatus();
		
		TodoStatus[] status = TodoStatus.values();
		
		Boolean statusFound = false;
		
		for(TodoStatus st:status)
		{
			if(st.getId().equals(reqStatus.getId()))
			{
				statusFound = true;
			}
		}
		
		if(!statusFound)
		{
			throw new ResourceNotFoundException("Invalid status");
		}
	}
}

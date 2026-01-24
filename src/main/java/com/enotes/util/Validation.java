package com.enotes.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.TodoDto;
import com.enotes.dto.TodoDto.StatusDto;
import com.enotes.dto.UserDto;
import com.enotes.entity.Role;
import com.enotes.enums.TodoStatus;
import com.enotes.exception.ExistDataException;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.exception.ValidationException;
import com.enotes.repository.RoleRepository;
import com.enotes.repository.UserRepository;


@Component
public class Validation {

	//private static final String EMAIL_REGEX = "^[a-zA-z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
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
	
	
	public void userValidation(UserDto userDto)
	{
		
		
		if(!StringUtils.hasText(userDto.getFirstName()))
		{
			throw new IllegalArgumentException("Fisrt name is invalid");
		}
		
		if(!StringUtils.hasText(userDto.getLastName()))
		{
			throw new IllegalArgumentException("Last name is invalid");
		}
		
		if(!StringUtils.hasText(userDto.getEmail()) || !userDto.getEmail().matches(Constants.EMAIL_REGEX))
		{
			throw new IllegalArgumentException("Email id is invalid");
		}
		else
		{
			//Duplicate email checking
			Boolean existEmail = userRepository.existsByEmail(userDto.getEmail());
			
			if(existEmail)
			{
				throw new ExistDataException("Email id already exist");
			}
		}
		
		if(!StringUtils.hasText(userDto.getMobno()) || !userDto.getMobno().matches(Constants.MOBNO_REGEX))
		{
			throw new IllegalArgumentException("Mobile number is invalid");
		}
		
		if (!StringUtils.hasText(userDto.getPassword()) ||
			    !userDto.getPassword().matches(Constants.PASSWORD_REGEX)) {

			    throw new IllegalArgumentException(
			        "Password must contain at least 8 characters, including uppercase, lowercase, number and special character"
			    );
			}
		
		if(CollectionUtils.isEmpty(userDto.getRoles()))
		{
			throw new IllegalArgumentException("Role is invalid");
		}
		else
		{
			List<Role> roles = roleRepository.findAll();
			
			List<Integer> roleIds = roles.stream().map(r -> r.getId()).toList();
			
			List<Integer> invalidReqRoleIds = userDto.getRoles().stream()
			.map(r -> r.getId())
			.filter(roleId -> !roleIds.contains(roleId)).toList();
			
			if(!CollectionUtils.isEmpty(invalidReqRoleIds))
			{
				throw new IllegalArgumentException("Role is invalid" + invalidReqRoleIds);
			}
		}
		
		
		
	}
}

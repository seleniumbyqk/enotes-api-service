package com.enotes.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.EmailRequest;
import com.enotes.dto.UserDto;
import com.enotes.entity.Role;
import com.enotes.entity.User;
import com.enotes.repository.RoleRepository;
import com.enotes.repository.UserRepository;
import com.enotes.util.Validation;

@Service
public class UserServiceImpl implements UserService{

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public Boolean register(UserDto userDto) throws Exception {
		// TODO Auto-generated method stub
		
		//Apply validation
		validation.userValidation(userDto);
		
		//Convert UserDto to User
		User user = modelMapper.map(userDto, User.class);
		
		//Role is a another table thats why created
		setRole(userDto, user);
		
		//Save in repository
		User saveUser = userRepository.save(user);
		
		//if user is empty throw exception
		if(!ObjectUtils.isEmpty(saveUser))
		{
			//Send email logic
			//Add dependency starter mail in pom.xml
			emailSend(saveUser);
			
			return true;
		}
		
		//If user empty
		return false;
	}

	private void emailSend(User saveUser) throws Exception {
		// TODO Auto-generated method stub
		
		String message = "Hi, <b>" + saveUser.getFirstName() + "</b>"
				+ " <br> Your account Register succesfully. <br>"
				+ "<br> Click the below link verify your account <br>"
				+ "<a href='#'>Click Here</a> <br><br>"
				+ "Thanks, <br> Enotes.com";
		
		EmailRequest emailRequest = EmailRequest.builder()
				.to(saveUser.getEmail())
				.title("Account Creating Confirmation")
				.subject("Account Created Successfully")
				.message(message)
				.build();
		
		emailService.sendEmail(emailRequest);
		
	}

	private void setRole(UserDto userDto, User user) {
		// TODO Auto-generated method stub
		
		List<Integer> reqRoleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
		
		List<Role> roles = roleRepository.findAllById(reqRoleId);
		
		user.setRoles(roles);
		
	}

}

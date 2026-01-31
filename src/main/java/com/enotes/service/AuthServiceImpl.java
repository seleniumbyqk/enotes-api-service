package com.enotes.service;

import java.util.List;
import java.util.UUID;

import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.config.security.CustomUserDetails;
import com.enotes.dto.EmailRequest;
import com.enotes.dto.LoginRequest;
import com.enotes.dto.LoginResponse;
import com.enotes.dto.UserRequest;
import com.enotes.dto.UserResponse;
import com.enotes.entity.AccountStatus;
import com.enotes.entity.Role;
import com.enotes.entity.User;
import com.enotes.repository.RoleRepository;
import com.enotes.repository.UserRepository;
import com.enotes.util.Validation;

@Service
public class AuthServiceImpl implements AuthService{

	
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
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public Boolean register(UserRequest userDto, String url) throws Exception {
		// TODO Auto-generated method stub
		
		//Apply validation
		validation.userValidation(userDto);
		
		//Convert UserRequest to User
		User user = modelMapper.map(userDto, User.class);
		
		//Role is a another table thats why created
		setRole(userDto, user);
		
		//Set isActive and create verification code
		AccountStatus status = AccountStatus.builder()
				.isActive(false)
				.verificationCode(UUID.randomUUID().toString())
				.build();
		
		user.setStatus(status);
		
		//Save password in encrypted for
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		//Save in repository
		User saveUser = userRepository.save(user);
		
		//if user is empty throw exception
		if(!ObjectUtils.isEmpty(saveUser))
		{
			//Send email logic
			//Add dependency starter mail in pom.xml
			emailSendForRegister(saveUser, url);
			
			return true;
		}
		
		//If user empty
		return false;
	}

	private void emailSendForRegister(User saveUser, String url) throws Exception {
		// TODO Auto-generated method stub
		//Static url
		
		String message = "Hi <b>[[username]],</b>"
				+ " <br> Your account Register succesfully. <br>"
				+ "<br> Click the below link to verify and activate your account <br>"
				+ "<a href='[[url]]'>Click Here</a> <br><br>"
				+ "Thanks, <br> Enotes.com";
		
		message = message.replace("[[username]]", saveUser.getFirstName());
		
		//Dynamic url -1
		//message = message.replace("[[url]]", "http://localhost:8081/api/v1/home/verify?uid=" 
		//+ saveUser.getId() + "&&code=" + saveUser.getStatus().getVerificationCode());
		
		//Dynamic url -2
		message = message.replace("[[url]]", url + "/api/v1/home/verify?uid=" 
				+ saveUser.getId() + "&&code=" + saveUser.getStatus().getVerificationCode());
		
		System.out.println("Dynamic url:"+ url);
		
		EmailRequest emailRequest = EmailRequest.builder()
				.to(saveUser.getEmail())
				.title("Account Creating Confirmation")
				.subject("Account Created Successfully")
				.message(message)
				.build();
		
		emailService.sendEmail(emailRequest);
		
	}

	private void setRole(UserRequest userDto, User user) {
		// TODO Auto-generated method stub
		
		List<Integer> reqRoleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
		
		List<Role> roles = roleRepository.findAllById(reqRoleId);
		
		user.setRoles(roles);
		
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		// TODO Auto-generated method stub
		
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		
		if(authenticate.isAuthenticated())
		{
			
			CustomUserDetails customUserDetails = (CustomUserDetails)authenticate.getPrincipal();
			
			//String token = "fhhfjhgkgyhhgvkhfj";
			
			//Call key generator
			String token = jwtService.generateToken(customUserDetails.getUser());
			
			LoginResponse loginResponse = LoginResponse.builder()
					.user(modelMapper.map(customUserDetails.getUser(), UserResponse.class))
					.token(token)
					.build();
			
			return loginResponse;
		}
		
		return null;
	}
	
	

}

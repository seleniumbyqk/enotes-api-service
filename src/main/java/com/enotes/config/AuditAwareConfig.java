package com.enotes.config;



import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.enotes.entity.User;
import com.enotes.util.CommonUtil;

public class AuditAwareConfig implements AuditorAware<Integer>{

	//Audit is required to automatic created by and updated by insert
	
	@Override
	public Optional<Integer> getCurrentAuditor() {
		// TODO Auto-generated method stub
		
		//Give logged in users id
		
		User loggedInUser = CommonUtil.getLoggedInUser();
		//return Optional.empty();
		
		
		//Provide logged in users id
		//return Optional.of(2);
		
		return Optional.of(loggedInUser.getId());
	}

}

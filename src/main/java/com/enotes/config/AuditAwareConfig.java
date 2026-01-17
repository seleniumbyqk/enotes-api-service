package com.enotes.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditAwareConfig implements AuditorAware<Integer>{

	//Audit is required to automatic created by and updated by insert
	
	@Override
	public Optional<Integer> getCurrentAuditor() {
		// TODO Auto-generated method stub
		
		//return Optional.empty();
		
		return Optional.of(1);
	}

}

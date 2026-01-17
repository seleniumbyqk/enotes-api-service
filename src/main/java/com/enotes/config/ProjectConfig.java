package com.enotes.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class ProjectConfig {

	//Automatic conversion of entity to dto and vice versa
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	
	//To implement the audit aware config class
	@Bean
	public AuditorAware<Integer> auditAware()
	{
		return new AuditAwareConfig();
	}
}

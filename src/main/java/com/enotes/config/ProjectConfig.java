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
	
	/*
	@Bean
    public ModelMapper modelMapper1() {

        ModelMapper modelMapper = new ModelMapper();

        // 🔥 Custom mapping for NotesDto -> Notes
        TypeMap<NotesDto, Notes> typeMap =
                modelMapper.createTypeMap(NotesDto.class, Notes.class);

        typeMap.addMappings(mapper ->
            mapper.map(
                src -> src.getCategory().getId(),
                (dest, value) -> {
                    Category category = new Category();
                    category.setId((Integer) value);
                    dest.setCategory(category);
                }
            )
        );

        return modelMapper;
    }
    */
}

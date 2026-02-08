package com.enotes.datasourceconfig;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.enotes.secondarydb",
                       //entityManagerFactoryRef = "postgresEntityManagerFactory",
                       //transactionManagerRef = "postgresTransactionManager")
public class SecondaryDataSourceConfig {

	@Autowired
	Environment env;
	
	@Bean
	@ConfigurationProperties("spring.postgres.datasource")
	public DataSource postgresDataSource()
	{
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("postgresDataSource") DataSource dataSource)
	{
		Map<String, Object> props = new HashMap<>();
		props.put("hibernate.hbm2ddl", env.getProperty("spring.postgres.jpa.hibernate.ddl-auto"));
		props.put("hibernate.dialect", env.getProperty("spring.postgres.jpa.properties.hibernate.dialect"));
		
		return builder.dataSource(dataSource)
				.packages("com.enotes.secondarydb")
				.properties(props)
				.persistenceUnit("postgres").build();
	}
	
	@Bean
	PlatformTransactionManager postgresTransactionManager(@Qualifier("postgresEntityManagerFactory") EntityManagerFactory emf)
	{
		return new JpaTransactionManager(emf);
	}
}

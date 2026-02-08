package com.enotes.datasourceconfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.enotes.primarydb",
                      // entityManagerFactoryRef = "mysqlEntityManagerFactory",
                      // transactionManagerRef = "mysqlTransactionManager")
public class PrimaryDataSourceConfig {

	
	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	public DataSource mysqlDataSource()
	{
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	@Primary
	LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("mysqlDataSource") DataSource dataSource)
	{
		
		return builder.dataSource(dataSource)
				.packages("com.enotes.primarydb")
				.persistenceUnit("mysql").build();
	}
	
	@Bean
	@Primary
	PlatformTransactionManager mysqlTransactionManager(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory emf)
	{
		return new JpaTransactionManager(emf);
	}
}

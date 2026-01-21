package com.enotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAware")          //To enable the auditing from basemodel class
@EnableScheduling                                           //To execute schedule
public class EnotesApiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnotesApiServiceApplication.class, args);
	}

}

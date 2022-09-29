package com.mangagod.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import com.mangagod.audit.AuditorAwareImpl;

@Configuration
@EnableJpaAuditing // a travez de esta anotacion se podra auditar las entidades
public class PersistenceConfiguration {

	@Bean
	public AuditorAware<String> auditorProviver(){
		return new AuditorAwareImpl();
	} 
	
}
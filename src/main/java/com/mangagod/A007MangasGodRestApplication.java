package com.mangagod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import de.pentabyte.springfox.ApiEnumDescriptionPlugin;

@SpringBootApplication
@EnableWebMvc
@EnableTransactionManagement
@Import(ApiEnumDescriptionPlugin.class) 
public class A007MangasGodRestApplication {
		
	public static void main(String[] args) {
		SpringApplication.run(A007MangasGodRestApplication.class, args);
	}
	
}
package com.asoprofarma.internos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class InternosApplication implements CommandLineRunner {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(InternosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String password ="12345";
		
		for(int i=0; i<4; i++) {
			String passwordBcrypt = passwordEncoder.encode(password);
			
		}
	}

}

package com.mballem.curso.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DemoSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSecurityApplication.class, args);

		System.out.println(new BCryptPasswordEncoder().encode("root"));
		System.out.println(new BCryptPasswordEncoder().encode("1234"));



	}
}

package com.no_country.fichaje;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = "com.no_country.fichaje", exclude = {SecurityAutoConfiguration.class})
@EnableWebMvc
@EnableJpaRepositories(basePackages = "com.no_country.fichaje.repository")
public class FichajeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FichajeApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			System.out.println("Paquete base: " + this.getClass().getPackage().getName());
		};
	}
}

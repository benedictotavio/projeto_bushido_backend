package br.org.institutobushido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class InstitutobushidoApplication {
	public static void main(String[] args) {
		SpringApplication.run(InstitutobushidoApplication.class, args);
	}
}
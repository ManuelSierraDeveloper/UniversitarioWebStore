package com.comercio.universitario;

import org.springframework.boot.SpringApplication;

public class TestUniversitarioApplication {

	public static void main(String[] args) {
		SpringApplication.from(UniversitarioApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}

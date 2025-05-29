package com.proy.VIBA;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class VibaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(VibaApplication.class, args);
	}

	
	@Override
	public void run (String... args) throws Exception{
		/*
		ClassCliente cli1 = new ClassCliente("75554012", "Alex", "Villanueva", "rolex@gmail.com", "995544112");
		repositorio.save(cli1);
	
		ClassCliente cli2 = new ClassCliente("74512584", "Andre", "Carrillo", "carrillo@gmail.com", "954125520");
		repositorio.save(cli2);
		*/
	}
	
	
}

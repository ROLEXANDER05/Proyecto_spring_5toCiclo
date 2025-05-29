package com.proy.VIBA.exception;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

	public static void main(String[] args) {
		 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        String rawPassword = "admin123"; // Contraseña sin encriptar
	        String encodedPassword = encoder.encode(rawPassword);
	        System.out.println("Contraseña encriptada: " + encodedPassword);
	}

}

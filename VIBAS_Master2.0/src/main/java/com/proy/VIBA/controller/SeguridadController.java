package com.proy.VIBA.controller;


import com.proy.VIBA.repositorio.CargoRepository;
import com.proy.VIBA.repositorio.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SeguridadController {
	@SuppressWarnings("unused")
	private final UserRepository userRepository;
	@SuppressWarnings("unused")
	private final CargoRepository cargoRepository;
    @SuppressWarnings("unused")
	private final PasswordEncoder passwordEncoder;

    public SeguridadController(UserRepository userRepository,CargoRepository cargoRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository; 
        this.cargoRepository = cargoRepository;
        this.passwordEncoder = passwordEncoder;
    }
   
    @GetMapping("/login")
    public String login() {
        return "login"; // login.html
    }

}

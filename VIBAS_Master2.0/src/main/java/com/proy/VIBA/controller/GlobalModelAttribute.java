package com.proy.VIBA.controller;


import com.proy.VIBA.modelo.ClassEmpleado;
import com.proy.VIBA.repositorio.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttribute {
	@Autowired
    private UserRepository userRepository;

    @ModelAttribute("user")
    public ClassEmpleado addAuthenticatedUserToModel() {
        // Obtener la autenticación actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Validar si el usuario está autenticado y no es anónimo
        if (authentication != null && authentication.isAuthenticated() 
            && !authentication.getName().equals("anonymousUser")) {
            // Buscar al usuario en la base de datos
            return userRepository.findByUsername(authentication.getName())
                    .orElse(null); // Si no encuentra al usuario, retorna null
        }

        return null; // Si no hay autenticación, retorna null
    }
}

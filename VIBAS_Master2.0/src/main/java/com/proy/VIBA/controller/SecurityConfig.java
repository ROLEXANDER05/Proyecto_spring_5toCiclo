package com.proy.VIBA.controller;

import com.proy.VIBA.servicio.UserService;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
    private final UserService userDetailsService;

    public SecurityConfig(UserService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/login", "/css/**", "/js/**").permitAll() // Recursos estáticos y login para todos
                                .requestMatchers("/vistasCliente/listarClientes", "/vistasProducto/listarProducto").hasAnyAuthority("ADMINISTRADOR", "USUARIO")
                                .requestMatchers("/vistasCliente/RegistrarCliente", "/vistasCliente/GuardarCliente").hasAnyAuthority("ADMINISTRADOR", "USUARIO")
                                .requestMatchers("/vistasCliente/editar/**", "/vistasCliente/eliminar/**", "/vistasCliente/exportarPDF").hasAuthority("ADMINISTRADOR")
                                .requestMatchers("/vistasProducto/RegistrarProducto", "/vistasProducto/GuardarProducto", "/vistasProducto/editar/**", "/vistasProducto/eliminar/**", "/vistasProducto/exportarPDF").hasAuthority("ADMINISTRADOR")
                                .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/vistasCliente/listarClientes", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
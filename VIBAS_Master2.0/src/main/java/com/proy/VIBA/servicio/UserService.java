package com.proy.VIBA.servicio;

import com.proy.VIBA.modelo.ClassEmpleado;
import com.proy.VIBA.repositorio.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
	    this.userRepository = userRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ClassEmpleado user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

		  return org.springframework.security.core.userdetails.User.builder()
				  .username(user.getUsername())
				  .password(user.getPassword())
				  .authorities(user.getCargo().getNombrecargo())
				  .build();
	}
	
}

package com.proy.VIBA.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.proy.VIBA.modelo.ClassEmpleado;


public interface UserRepository extends JpaRepository<ClassEmpleado, Long> {
	Optional<ClassEmpleado> findByUsername(String username);
}

package com.proy.VIBA.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proy.VIBA.modelo.ClassEmpleado;

public interface EmpleadoRepositorio extends JpaRepository<ClassEmpleado, Integer> {
	
	@Query("SELECT c FROM ClassEmpleado c WHERE c.dni LIKE %?1%"
			+ " OR c.nombre LIKE %?1%"
			+ " OR c.apellido LIKE %?1%"
			+ " OR c.telefono LIKE %?1%")
	public List<ClassEmpleado> buscarDNI(String busqueda);
	
	ClassEmpleado findByDni(String dni);
	
	ClassEmpleado findByUsername(String username);
	
	Page<ClassEmpleado> findByNombreContainingOrApellidoContaining(String nombre, String apellido, Pageable pageable);
	
}//FIn

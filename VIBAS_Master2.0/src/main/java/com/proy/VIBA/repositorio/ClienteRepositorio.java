package com.proy.VIBA.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proy.VIBA.modelo.ClassCliente;

@Repository
public interface ClienteRepositorio extends JpaRepository<ClassCliente, Integer> {

	@Query("SELECT c FROM ClassCliente c WHERE c.dni LIKE %?1%"
			+ " OR c.nombre LIKE %?1%"
			+ " OR c.apellido LIKE %?1%"
			+ " OR c.telefono LIKE %?1%")
	public List<ClassCliente> buscarDNI(String busqueda);
	
	ClassCliente findByDni(String dni);
	
	Page<ClassCliente> findByNombreContainingOrApellidoContaining(String nombre, String apellido, Pageable pageable);
	
	
}

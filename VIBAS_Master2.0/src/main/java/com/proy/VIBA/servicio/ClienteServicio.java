package com.proy.VIBA.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proy.VIBA.modelo.ClassCliente;

public interface ClienteServicio {

	public List<ClassCliente> listarClientes(String busqueda);
	public ClassCliente CrearCliente(ClassCliente cliente);
	public ClassCliente BuscarClienteId(Integer id);
	public void EliminarCliente(Integer id);
	
	ClassCliente findByDni(String dni);
	public List<ClassCliente> findAll();
	
	Page<ClassCliente> listarClientesPaginado(String busqueda,Pageable pageable);
	
}

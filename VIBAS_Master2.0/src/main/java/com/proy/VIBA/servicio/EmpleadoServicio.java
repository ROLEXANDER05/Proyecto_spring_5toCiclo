package com.proy.VIBA.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proy.VIBA.modelo.ClassEmpleado;

public interface EmpleadoServicio {
	
	public List<ClassEmpleado> listarEmpleado(String busqueda);
	public ClassEmpleado CrearEmpleado(ClassEmpleado empleado);
	public ClassEmpleado BuscarEmpleadoId(Integer id);
	public void EliminarEmpleado(Integer id);
	ClassEmpleado findByDni(String dni);
	public List<ClassEmpleado> findAll();

	ClassEmpleado findByUsername(String username);
	Page<ClassEmpleado> listarEmpleadoPaginado(String busqueda,Pageable pageable);
	
}

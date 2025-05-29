package com.proy.VIBA.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proy.VIBA.modelo.ClassEmpleado;
import com.proy.VIBA.repositorio.EmpleadoRepositorio;


@Service
public class EmpleadoServicioImp implements EmpleadoServicio{

	//implementamos la inyeccion de dependecia..
	@Autowired
	private EmpleadoRepositorio repositorio;
	
	@Override
	public List<ClassEmpleado> listarEmpleado(String busqueda) {
		if(busqueda != null) {
			return repositorio.buscarDNI(busqueda);
		}
		return repositorio.findAll();
	}

	@Override
	public Page<ClassEmpleado> listarEmpleadoPaginado(String busqueda, Pageable pageable) {
		if(busqueda != null && !busqueda.isEmpty()) {
			return repositorio.findByNombreContainingOrApellidoContaining(busqueda, busqueda, pageable);
		} else {
			return repositorio.findAll(pageable);
		}
	}
	
	@Override
	public ClassEmpleado CrearEmpleado(ClassEmpleado empleado) {
		return repositorio.save(empleado);
	}

	@Override
	public ClassEmpleado BuscarEmpleadoId(Integer id) {
		return repositorio.findById(id).get();
	}

	@Override
	public void EliminarEmpleado(Integer id) {
		repositorio.deleteById(id);
	}

	@Override
	public ClassEmpleado findByDni(String dni) {
		return repositorio.findByDni(dni);
	}

	@Override
	public List<ClassEmpleado> findAll() {
		return repositorio.findAll();
	}
	
	@Override
	public ClassEmpleado findByUsername(String username) {
		return repositorio.findByUsername(username);
	}



	
	
}

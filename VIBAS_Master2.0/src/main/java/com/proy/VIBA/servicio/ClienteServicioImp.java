package com.proy.VIBA.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proy.VIBA.modelo.ClassCliente;
import com.proy.VIBA.repositorio.ClienteRepositorio;


@Service
public class ClienteServicioImp implements ClienteServicio {

	
	@Autowired
	private ClienteRepositorio repositorio;
	
	@Override
	public List<ClassCliente> listarClientes(String busqueda) {
		if(busqueda != null) {
			return repositorio.buscarDNI(busqueda);
		}
		return repositorio.findAll();
	}
	
	@Override
	public Page<ClassCliente> listarClientesPaginado(String busqueda, Pageable pageable){
		if(busqueda != null && !busqueda.isEmpty()) {
			return repositorio.findByNombreContainingOrApellidoContaining(busqueda, busqueda, pageable);
		} else {
			return repositorio.findAll(pageable);
		}
		
	}
	
	

	@Override
	public ClassCliente CrearCliente(ClassCliente cliente) {
		
		return repositorio.save(cliente);
	}

	@Override
	public ClassCliente BuscarClienteId(Integer id) {
		
		return repositorio.findById(id).get();
	}

	@Override
	public void EliminarCliente(Integer id) {
		repositorio.deleteById(id);
	}

	@Override
	public ClassCliente findByDni(String dni) {
		return repositorio.findByDni(dni);
	}

	@Override
	public List<ClassCliente> findAll() {
		return repositorio.findAll();
		
	}

	

	

	
	
	
}

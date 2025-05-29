package com.proy.VIBA.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proy.VIBA.modelo.ClassProducto;
import com.proy.VIBA.repositorio.ProductoRepositorio;

@Service
public class ProductoServicioImp implements ProductoServicio {
	
	@Autowired
	private ProductoRepositorio repositorio;
	
	@Override
	public List<ClassProducto> listarProducto(String busqueda) {
		if(busqueda != null) {
			return repositorio.buscarProducto(busqueda);
		}
		return repositorio.findAll();
	}
	
	@Override
	public Page<ClassProducto> listarProductoPaginado(String busqueda, Pageable pageable) {
		if(busqueda != null && !busqueda.isEmpty()) {
			return repositorio.findByNomproductoContaining(busqueda, pageable);
		} else {
			return repositorio.findAll(pageable);
		}
	}

	@Override
	public ClassProducto CrearProducto(ClassProducto producto) {
		return repositorio.save(producto);
	}

	@Override
	public ClassProducto BuscarProductoId(Integer id) {
		return repositorio.findById(id).get();
	}

	@Override
	public void EliminarProducto(Integer id) {
		repositorio.deleteById(id);
		
	}
	
	@Override
	public void save(ClassProducto producto) {
		repositorio.save(producto);
	}

	@Override
	public List<ClassProducto> findAll() {
		
		return repositorio.findAll();
	}


}

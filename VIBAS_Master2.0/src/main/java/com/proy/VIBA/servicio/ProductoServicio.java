package com.proy.VIBA.servicio;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proy.VIBA.modelo.ClassProducto;

public interface ProductoServicio {

	public List<ClassProducto> listarProducto(String busqueda);
	public ClassProducto CrearProducto(ClassProducto producto);
	public ClassProducto BuscarProductoId(Integer id);
	public void EliminarProducto(Integer id);
	
	public void save(ClassProducto producto);
	public List<ClassProducto> findAll();
	
	Page<ClassProducto> listarProductoPaginado(String busqueda,Pageable pageable);
	
	
}

package com.proy.VIBA.servicio;

import org.springframework.beans.factory.annotation.Autowired;

import com.proy.VIBA.modelo.ClassDetalleVenta;
import com.proy.VIBA.repositorio.DetalleVentaRepositorio;

public class DetalleVentaServicioImp implements DetalleVentaServicio {

	@Autowired
	private DetalleVentaRepositorio repositorio;
	
	@Override
	public void save(ClassDetalleVenta detalleVenta) {

		repositorio.save(detalleVenta);
		
	}

}

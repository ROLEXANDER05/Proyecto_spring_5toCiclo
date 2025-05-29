package com.proy.VIBA.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proy.VIBA.modelo.ClassDetalleVenta;

public interface DetalleVentaRepositorio 
extends JpaRepository<ClassDetalleVenta, Long>{

}

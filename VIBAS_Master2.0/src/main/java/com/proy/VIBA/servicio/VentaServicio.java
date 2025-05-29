package com.proy.VIBA.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proy.VIBA.modelo.ClassVenta;

import jakarta.servlet.http.HttpSession;

public interface VentaServicio {

	public void save(ClassVenta venta, 
					Double monto, 
					HttpSession session);
	
	List<ClassVenta> listarVentas();
	
	ClassVenta obtenerVentaConDetalles(Long codigoVenta);

	public ClassVenta obtenerPorId(Long id);
	
	Page<ClassVenta> listarVentasPaginado(String busqueda, Pageable pageable);
	
	
}
